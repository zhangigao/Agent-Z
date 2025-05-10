package org.zhj.agentz.application.conversation.service;

import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import dev.langchain4j.model.output.TokenUsage;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.zhj.agentz.application.conversation.assembler.MessageAssembler;
import org.zhj.agentz.application.conversation.dto.ChatRequest;
import org.zhj.agentz.application.conversation.dto.StreamChatResponse;
import org.zhj.agentz.domain.agent.model.AgentEntity;
import org.zhj.agentz.domain.agent.model.AgentWorkspaceEntity;
import org.zhj.agentz.domain.conversation.constant.Role;
import org.zhj.agentz.domain.conversation.dto.MessageDTO;
import org.zhj.agentz.domain.conversation.model.MessageEntity;
import org.zhj.agentz.domain.conversation.model.SessionEntity;
import org.zhj.agentz.domain.conversation.service.ConversationDomainService;
import org.zhj.agentz.domain.conversation.service.SessionDomainService;
import org.zhj.agentz.domain.llm.model.ModelEntity;
import org.zhj.agentz.domain.llm.model.ProviderEntity;
import org.zhj.agentz.domain.llm.model.config.ProviderConfig;
import org.zhj.agentz.domain.llm.service.LLMDomainService;
import org.zhj.agentz.domain.service.AgentDomainService;
import org.zhj.agentz.domain.service.AgentWorkspaceDomainService;
import org.zhj.agentz.infrastructure.exception.BusinessException;
import org.zhj.agentz.infrastructure.llm.protocol.LLMProviderService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


/**
 * 对话应用服务，用于适配域层的对话服务
 */
@Service
public class ConversationAppService {

    private final ConversationDomainService conversationDomainService;
    private final SessionDomainService sessionDomainService;
    private final AgentDomainService agentDomainService;
    private final AgentWorkspaceDomainService agentWorkspaceDomainService;
    private final LLMDomainService llmDomainService;
    private final LLMProviderService llmProviderService;

    public ConversationAppService(
            ConversationDomainService conversationDomainService,
            SessionDomainService sessionDomainService, AgentDomainService agentDomainService, AgentWorkspaceDomainService agentWorkspaceDomainService, LLMDomainService llmDomainService, LLMProviderService llmService) {
        this.conversationDomainService = conversationDomainService;
        this.sessionDomainService = sessionDomainService;
        this.agentDomainService = agentDomainService;
        this.agentWorkspaceDomainService = agentWorkspaceDomainService;
        this.llmDomainService = llmDomainService;
        this.llmProviderService = llmService;
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

        List<MessageEntity> conversationMessages = conversationDomainService.getConversationMessages(sessionId);
        return MessageAssembler.toDTOs(conversationMessages);
    }


    public SseEmitter chat(ChatRequest chatRequest, String userId){

        // 获取会话
        String sessionId = chatRequest.getSessionId();
        SessionEntity session = sessionDomainService.getSession(sessionId, userId);
        String agentId = session.getAgentId();

        // 获取对应agent是否可以使用：如果 userId 不同并且是禁用，则不可对话
        AgentEntity agent = agentDomainService.getAgentById(agentId);
        if (!agent.getUserId().equals(userId) && !agent.getEnabled()){
            throw new BusinessException("agent已被禁用");
        }

        // 从工作区中获取对应的模型信息
        AgentWorkspaceEntity workspace = agentWorkspaceDomainService.getWorkspace(agentId, userId);
        String modelId = workspace.getModelId();
        ModelEntity model = llmDomainService.getModelById(modelId);

        model.isActive();

        // 获取服务商信息
        ProviderEntity provider = llmDomainService.getProvider(model.getProviderId(), userId);
        provider.isActive();

        // 对话 todo 这里需要传入消息列表 ，并且目前默认流式
        ProviderConfig config = provider.getConfig();
        StreamingChatLanguageModel chatStreamClient = llmProviderService.getStream(provider.getProtocol(),
                new org.zhj.agentz.infrastructure.llm.config.ProviderConfig(config.getApiKey(),config.getBaseUrl(),model.getModelId()));

        // 用户消息
        MessageEntity userMessageEntity = new MessageEntity();
        userMessageEntity.setRole(Role.USER);
        userMessageEntity.setContent(chatRequest.getMessage());
        userMessageEntity.setSessionId(sessionId);

        // 大模型消息
        MessageEntity llmMessageEntity = new MessageEntity();
        llmMessageEntity.setRole(Role.ASSISTANT);
        llmMessageEntity.setSessionId(sessionId);
        llmMessageEntity.setModel(model.getModelId());
        llmMessageEntity.setProvider(provider.getId());

        SseEmitter emitter = new SseEmitter();

        chatStreamClient.chat(chatRequest.getMessage(), new StreamingChatResponseHandler() {
            @Override
            public void onPartialResponse(String partialResponse) {
                try {
                    StreamChatResponse response = new StreamChatResponse();
                    response.setContent(partialResponse);
                    response.setDone(false);
                    response.setProvider(provider.getName());
                    response.setModel(model.getModelId());
                    emitter.send(response);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onCompleteResponse(ChatResponse completeResponse) {
                // todo 传出去
                TokenUsage tokenUsage = completeResponse.metadata().tokenUsage();

                Integer inputTokenCount = tokenUsage.inputTokenCount();
                userMessageEntity.setTokenCount(inputTokenCount);
                Integer outputTokenCount = tokenUsage.outputTokenCount();
                llmMessageEntity.setTokenCount(outputTokenCount);
                llmMessageEntity.setContent(completeResponse.aiMessage().text());
                try {
                    StreamChatResponse response = new StreamChatResponse();
                    response.setContent("");
                    response.setDone(true);
                    response.setProvider(provider.getName());
                    response.setModel(model.getModelId());
                    emitter.send(response);
                    emitter.complete();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                conversationDomainService.insertBathMessage(Arrays.asList(userMessageEntity,llmMessageEntity));
            }

            @Override
            public void onError(Throwable error) {
                try {
                    StreamChatResponse response = new StreamChatResponse();
                    response.setContent(error.getMessage());
                    response.setDone(true);
                    emitter.send(response);
                    emitter.complete();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return emitter;
    }
}