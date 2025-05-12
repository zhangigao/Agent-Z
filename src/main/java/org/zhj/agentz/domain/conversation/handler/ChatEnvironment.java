package org.zhj.agentz.domain.conversation.handler;

import org.zhj.agentz.application.conversation.service.service.handler.context.ChatContext;
import org.zhj.agentz.domain.agent.model.AgentEntity;
import org.zhj.agentz.domain.agent.model.LLMModelConfig;
import org.zhj.agentz.domain.conversation.model.ContextEntity;
import org.zhj.agentz.domain.conversation.model.MessageEntity;
import org.zhj.agentz.domain.llm.model.ModelEntity;
import org.zhj.agentz.domain.llm.model.ProviderEntity;

import java.util.List;

/**
 * 对话环境，包含对话所需的所有信息
 *
 * @Author 86155
 * @Date 2025/5/11
 */
public class ChatEnvironment extends ChatContext {
    /**
     * 会话ID
     */
    private String sessionId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户消息
     */
    private String userMessage;

    /**
     * 智能体实体
     */
    private AgentEntity agent;

    /**
     * 模型实体
     */
    private ModelEntity model;

    /**
     * 服务商实体
     */
    private ProviderEntity provider;

    /**
     * 大模型配置
     */
    private LLMModelConfig llmModelConfig;

    /**
     * 上下文实体
     */
    private ContextEntity contextEntity;

    /**
     * 历史消息列表
     */
    private List<MessageEntity> messageHistory;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public AgentEntity getAgent() {
        return agent;
    }

    public void setAgent(AgentEntity agent) {
        this.agent = agent;
    }

    public ModelEntity getModel() {
        return model;
    }

    public void setModel(ModelEntity model) {
        this.model = model;
    }

    public ProviderEntity getProvider() {
        return provider;
    }

    public void setProvider(ProviderEntity provider) {
        this.provider = provider;
    }

    public LLMModelConfig getLlmModelConfig() {
        return llmModelConfig;
    }

    public void setLlmModelConfig(LLMModelConfig llmModelConfig) {
        this.llmModelConfig = llmModelConfig;
    }

    public ContextEntity getContextEntity() {
        return contextEntity;
    }

    public void setContextEntity(ContextEntity contextEntity) {
        this.contextEntity = contextEntity;
    }

    public List<MessageEntity> getMessageHistory() {
        return messageHistory;
    }

    public void setMessageHistory(List<MessageEntity> messageHistory) {
        this.messageHistory = messageHistory;
    }
}
