package org.zhj.agentz.application.conversation.service;

import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.zhj.agentz.application.conversation.dto.ChatRequest;
import org.zhj.agentz.application.conversation.dto.ChatResponse;
import org.zhj.agentz.domain.llm.model.LLMRequest;
import org.zhj.agentz.domain.llm.model.LLMResponse;
import org.zhj.agentz.domain.llm.service.LLMService;


import java.util.Map;

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
