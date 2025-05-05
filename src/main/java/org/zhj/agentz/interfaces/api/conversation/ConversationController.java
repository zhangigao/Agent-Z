package org.zhj.agentz.interfaces.api.conversation;

import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.zhj.agentz.application.conversation.dto.ChatRequest;
import org.zhj.agentz.application.conversation.dto.ChatResponse;
import org.zhj.agentz.application.conversation.dto.StreamChatRequest;
import org.zhj.agentz.application.conversation.service.ConversationService;
import org.zhj.agentz.interfaces.api.common.Result;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 对话控制器
 */
@RestController
@RequestMapping("/conversation")
public class ConversationController {
    
    private final Logger logger = LoggerFactory.getLogger(ConversationController.class);

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Resource
    private ConversationService conversationService;

    /**
     * 普通聊天接口
     *
     * @param request 聊天请求
     * @return 聊天响应
     */
    @PostMapping("/chat")
    public Result<ChatResponse> chat(@RequestBody @Validated ChatRequest request) {
        logger.info("收到聊天请求: {}, 服务商: {}, 模型: {}", 
                request.getMessage(), 
                request.getProvider() != null ? request.getProvider() : "默认", 
                request.getModel() != null ? request.getModel() : "默认");
        
        // 如果请求没有指定服务商，默认使用SiliconFlow
        if (request.getProvider() == null || request.getProvider().isEmpty()) {
            request.setProvider("siliconflow");
        }
        
        try {
            ChatResponse response = conversationService.chat(request);
            return Result.success(response);
        } catch (Exception e) {
            logger.error("处理聊天请求异常", e);
            return Result.serverError("处理请求失败: " + e.getMessage());
        }
    }

    /**
     * 流式聊天接口，使用SSE (Server-Sent Events) - POST方式
     *
     * @param request 流式聊天请求
     * @return SSE流式响应
     */
    @PostMapping("/chat/stream")
    public SseEmitter chatSteam(@RequestBody @Validated StreamChatRequest request) {
        logger.info("收到聊天请求：{}，服务商：{}，模型：{}",
                request.getMessage(),
                request.getProvider() != null ? request.getProvider() : "默认",
                request.getModel() != null ? request.getModel() : "默认");
        // 如果请求没有指定服务商，默认使用SiliconFlow
        if (request.getProvider() == null || request.getProvider().isEmpty()) {
            request.setProvider("siliconflow");
        }
        // 创建SseEmitter，超时时间设置为5分钟
        SseEmitter emitter = new SseEmitter(300000L);

        // 设置超时回调
        emitter.onTimeout(() -> {
            logger.warn("流式聊天请求超时：{}", request.getMessage());
        });

        // 设置完成回调
        emitter.onCompletion(() -> {
            logger.info("流式聊天请求完成：{}", request.getMessage());
        });

        // 设置错误回调
        emitter.onError(ex -> {
            logger.error("流式聊天请求错误", ex);
        });
        executorService.execute(() -> {
            try {
                // 使用新的真正流式实现
                conversationService.chatStream(request, (response, isLast) -> {
                    try {
                        // 发送每个响应块到客户端
                        emitter.send(response);

                        // 如果是最后一个响应块，完成请求
                        if (isLast) {
                            emitter.complete();
                        }
                    } catch (IOException e) {
                        logger.error("发送流式响应块时出错", e);
                        emitter.completeWithError(e);
                    }
                });
            } catch (Exception e) {
                logger.error("处理流式聊天请求发生异常", e);
                emitter.completeWithError(e);
            }
        });
        return emitter;
    }
    /**
     * 健康检查接口
     *
     * @return 状态信息
     */
    @GetMapping("/health")
    public Result<Object> health() {
        return Result.success("服务正常运行中");
    }
}
