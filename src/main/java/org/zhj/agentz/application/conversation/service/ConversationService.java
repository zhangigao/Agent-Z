package org.zhj.agentz.application.conversation.service;

import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.zhj.agentz.application.conversation.dto.ChatRequest;
import org.zhj.agentz.application.conversation.dto.ChatResponse;
import org.zhj.agentz.application.conversation.dto.StreamChatRequest;
import org.zhj.agentz.application.conversation.dto.StreamChatResponse;
import org.zhj.agentz.domain.llm.model.LLMRequest;
import org.zhj.agentz.domain.llm.model.LLMResponse;
import org.zhj.agentz.domain.llm.service.LLMService;
import org.zhj.agentz.infrastructure.integration.llm.siliconflow.SiliconFlowLlmService;


import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * 对话服务
 */
@Service
public class ConversationService {
    
    private final Logger logger = LoggerFactory.getLogger(ConversationService.class);
    
    @Resource
    private LLMService defaultLLMService;
    
    @Resource
    private Map<String, LLMService> llmServiceMap;
    
    /**
     * 处理普通聊天请求
     *
     * @param request 聊天请求
     * @return 聊天响应
     */
    public ChatResponse chat(ChatRequest request) {
        logger.info("接收到聊天请求: {}", request.getMessage());
        
        LLMService llmService = getLLMService(request.getProvider());
        
        LLMRequest llmRequest = new LLMRequest();
        llmRequest.addUserMessage(request.getMessage());
        
        if (request.getModel() != null && !request.getModel().isEmpty()) {
            logger.info("用户指定模型: {}", request.getModel());
            llmRequest.setModel(request.getModel());
        } else {
            logger.info("使用默认模型: {}", llmService.getDefaultModel());
        }
        
        LLMResponse llmResponse = llmService.chat(llmRequest);
        
        ChatResponse response = new ChatResponse();
        response.setContent(llmResponse.getContent());
        response.setProvider(llmResponse.getProvider());
        response.setModel(llmResponse.getModel());
        response.setSessionId(request.getSessionId());
        
        return response;
    }

    /**
     * 处理流式聊天请求，使用回调处理响应
     *
     * @param request         流式聊天请求
     * @param responseHandler 响应处理回调
     */
    public void chatStream(StreamChatRequest request, BiConsumer<StreamChatResponse, Boolean> responseHandler) {
        logger.info("接收到真实流式聊天请求: {}", request.getMessage());

        LLMService llmService = getLLMService(request.getProvider());

        LLMRequest llmRequest = new LLMRequest();
        llmRequest.addUserMessage(request.getMessage());

        // 确保设置流式参数为true
        llmRequest.setStream(true);

        if (request.getModel() != null && !request.getModel().isEmpty()) {
            logger.info("用户指定模型: {}", request.getModel());
            llmRequest.setModel(request.getModel());
        } else {
            logger.info("使用默认模型: {}", llmService.getDefaultModel());
        }

        try {
            // 检查LLM服务是否为SiliconFlowLlmService以使用其回调接口
            if (llmService instanceof SiliconFlowLlmService siliconFlowService) {
                logger.info("使用SiliconFlow的真实流式响应");
                // 使用回调接口
                siliconFlowService.streamChat(llmRequest, (chunk, isLast) -> {
                    StreamChatResponse response = new StreamChatResponse();
                    response.setContent(chunk);
                    response.setDone(isLast);
                    response.setProvider(llmService.getProviderName());
                    response.setModel(
                            llmRequest.getModel() != null ? llmRequest.getModel() : llmService.getDefaultModel());
                    response.setSessionId(request.getSessionId());

                    // 调用响应处理回调
                    responseHandler.accept(response, isLast);
                });
            } else {
                // 对于不支持回调的LLM服务，使用原来的方式
                logger.info("服务商不支持真实流式，使用传统分块方式");
                List<String> chunks = llmService.chatStreamList(llmRequest);

                // 转换为流式响应
                for (int i = 0; i < chunks.size(); i++) {
                    boolean isLast = (i == chunks.size() - 1);

                    StreamChatResponse response = new StreamChatResponse();
                    response.setContent(chunks.get(i));
                    response.setDone(isLast);
                    response.setProvider(llmService.getProviderName());
                    response.setModel(
                            llmRequest.getModel() != null ? llmRequest.getModel() : llmService.getDefaultModel());
                    response.setSessionId(request.getSessionId());

                    // 调用响应处理回调
                    responseHandler.accept(response, isLast);
                }
            }

        } catch (Exception e) {
            logger.error("处理流式聊天请求异常", e);
            // 发生异常时，返回一个错误响应
            StreamChatResponse errorResponse = new StreamChatResponse();
            errorResponse.setContent("处理请求时发生错误: " + e.getMessage());
            errorResponse.setDone(true);
            errorResponse.setProvider(llmService.getProviderName());
            errorResponse
                    .setModel(llmRequest.getModel() != null ? llmRequest.getModel() : llmService.getDefaultModel());
            errorResponse.setSessionId(request.getSessionId());

            // 调用响应处理回调，并标记为最后一个
            responseHandler.accept(errorResponse, true);
        }
    }
    
    /**
     * 获取对应的LLM服务
     *
     * @param provider 服务商名称
     * @return LLM服务
     */
    private LLMService getLLMService(String provider) {
        if (provider == null || provider.isEmpty()) {
            logger.info("使用默认LLM服务: {}", defaultLLMService.getProviderName());
            return defaultLLMService;
        }
        
        String serviceName = provider.toLowerCase() + "LLMService";
        logger.debug("尝试获取服务: {}", serviceName);
        
        LLMService service = llmServiceMap.get(serviceName);
        
        if (service == null) {
            logger.warn("未找到服务商 [{}] 的实现，使用默认服务商: {}", provider, defaultLLMService.getProviderName());
            return defaultLLMService;
        }
        
        logger.info("使用服务商: {}", service.getProviderName());
        return service;
    }
}
