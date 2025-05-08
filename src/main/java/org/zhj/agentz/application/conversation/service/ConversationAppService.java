package org.zhj.agentz.application.conversation.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.tinylog.pattern.Token;
import org.zhj.agentz.application.conversation.dto.StreamChatRequest;
import org.zhj.agentz.application.conversation.dto.StreamChatResponse;
import org.zhj.agentz.domain.conversation.dto.MessageDTO;
import org.zhj.agentz.domain.conversation.model.SessionEntity;
import org.zhj.agentz.domain.conversation.service.ConversationDomainService;
import org.zhj.agentz.domain.conversation.service.SessionDomainService;
import org.zhj.agentz.domain.service.AgentDomainService;
import org.zhj.agentz.domain.token.service.TokenDomainService;
import org.zhj.agentz.infrastructure.exception.BusinessException;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;


/**
 * 对话应用服务，用于适配域层的对话服务
 */
@Service
public class ConversationAppService {

    private final ConversationDomainService conversationDomainService;
    private final SessionDomainService sessionDomainService;
    private final TokenDomainService tokenDomainService;
    private final AgentDomainService agentDomainService;


    public ConversationAppService(
            ConversationDomainService conversationDomainService,
            SessionDomainService sessionDomainService,
            TokenDomainService tokenDomainService,
            AgentDomainService agentDomainService) {
        this.conversationDomainService = conversationDomainService;
        this.sessionDomainService = sessionDomainService;
        this.tokenDomainService = tokenDomainService;
        this.agentDomainService = agentDomainService;
    }

    /**
     * 处理流式聊天请求
     */
    public void chatStream(StreamChatRequest request, BiConsumer<StreamChatResponse, Boolean> responseHandler) {
        conversationDomainService.chatStream(request, responseHandler);
    }

    public void saveAssistantMessage(String sessionId, String content,
                                     String provider, String model, Integer tokenCount) {
        conversationDomainService.saveAssistantMessage(sessionId, content, provider, model, tokenCount);
    }

    /**
     * 发送消息 - 保存用户消息并创建或更新上下文
     *
     * @param sessionId 会话id
     * @param userId    用户id
     * @param message   消息内容
     * @param modelName 模型名称
     */
    public void sendMessage(String sessionId, String userId, String message, String modelName) {
        conversationDomainService.sendMessage(sessionId, userId, message, modelName);
    }


    /**
     * 获取会话中的消息列表
     *
     * @param sessionId 会话id
     * @param userId    用户id
     * @return 消息列表
     */
    public List<MessageDTO> getConversationMessages(String sessionId, String userId) {

        // 查询对应会话是否存在
        SessionEntity sessionEntity = sessionDomainService.find(sessionId, userId);

        if (sessionEntity == null){
            throw new BusinessException("会话不存在");
        }

        return conversationDomainService.getConversationMessages(sessionId);
    }
}