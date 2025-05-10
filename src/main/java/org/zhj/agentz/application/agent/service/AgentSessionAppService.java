package org.zhj.agentz.application.agent.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zhj.agentz.application.conversation.assembler.SessionAssembler;
import org.zhj.agentz.domain.agent.model.AgentEntity;
import org.zhj.agentz.domain.conversation.constant.Role;
import org.zhj.agentz.domain.conversation.dto.SessionDTO;
import org.zhj.agentz.domain.conversation.model.MessageEntity;
import org.zhj.agentz.domain.conversation.model.SessionEntity;
import org.zhj.agentz.domain.conversation.service.ConversationDomainService;
import org.zhj.agentz.domain.conversation.service.SessionDomainService;
import org.zhj.agentz.domain.service.AgentDomainService;
import org.zhj.agentz.domain.service.AgentWorkspaceDomainService;
import org.zhj.agentz.infrastructure.exception.BusinessException;
import org.zhj.agentz.interfaces.dto.conversation.ConversationRequest;

import java.util.List;

@Service
public class AgentSessionAppService {

    private final AgentWorkspaceDomainService agentWorkspaceDomainService;

    private final AgentDomainService agentServiceDomainService;

    private final SessionDomainService sessionDomainService;

    private final ConversationDomainService conversationDomainService;

    public AgentSessionAppService(AgentWorkspaceDomainService agentWorkspaceDomainService,
                                  AgentDomainService agentServiceDomainService,
                                  SessionDomainService sessionDomainService,
                                  ConversationDomainService conversationDomainService) {
        this.agentWorkspaceDomainService = agentWorkspaceDomainService;
        this.agentServiceDomainService = agentServiceDomainService;
        this.sessionDomainService = sessionDomainService;
        this.conversationDomainService = conversationDomainService;
    }

    /**
     * 获取助理下的会话列表
     *
     * @param userId  用户id
     * @param agentId 助理id
     * @return 会话列表
     */
    public List<SessionDTO> getAgentSessionList(String userId, String agentId) {

        // 校验该 agent 是否被添加了工作区，判断条件：是否是自己的助理 or 在工作区中
        boolean b = agentServiceDomainService.exist(agentId, userId);
        boolean b1 = agentWorkspaceDomainService.exist(agentId, userId);

        if (!b && !b1){
            throw new BusinessException("助理不存在");
        }

        // 获取对应的会话列表
        List<SessionEntity> sessions = sessionDomainService.getSessionsByAgentId(agentId);
        if (sessions.isEmpty()) {
            // 如果会话列表为空，则新创建一个并且返回
            SessionEntity session = sessionDomainService.createSession(agentId, userId);
            sessions.add(session);
        }
        return SessionAssembler.toDTOs(sessions);
    }

    /**
     * 创建会话
     *
     * @param userId  用户id
     * @param agentId 助理id
     * @return 会话
     */
    public SessionDTO createSession(String userId, String agentId) {
        SessionEntity session = sessionDomainService.createSession(agentId, userId);
        AgentEntity agent = agentServiceDomainService.getAgentWithPermissionCheck(agentId, userId);
        String welcomeMessage = agent.getWelcomeMessage();
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setRole(Role.SYSTEM);
        messageEntity.setContent(welcomeMessage);
        messageEntity.setSessionId(session.getId());
        conversationDomainService.saveMessage(messageEntity);
        return SessionAssembler.toDTO(session);
    }



    /**
     * 更新会话
     *
     * @param id     会话id
     * @param userId 用户id
     * @param title  标题
     */
    public void updateSession(String id, String userId, String title) {
        sessionDomainService.updateSession(id, userId, title);
    }

    /**
     * 删除会话
     *
     * @param id 会话id
     */
    @Transactional
    public void deleteSession(String id, String userId) {
        sessionDomainService.deleteSession(id, userId);

        // 删除会话下的消息
        conversationDomainService.deleteConversationMessages(id);
    }

    /**
     * 发送消息
     *
     * @param id 会话id
     * @param userId 用户id
     * @param conversationRequest 会话请求
     */
    public void sendMessage(String id, String userId, ConversationRequest conversationRequest) {

        // todo 目前先普通的发送消息，后续还需要根据 agent 的记忆策略，对话助手/agent 策略进行处理

//        // 查询会话是否存在,根据id和userid
//        String agentId = conversationRequest.getAgentId();
//        AgentDTO agent = agentServiceDomainService.getAgent(agentId, userId);
//
//        // todo 目前是模型名称，后续需要换为模型 id
//        ModelConfig modelConfig = agent.getModelConfig();
//        if (StringUtils.isEmpty(modelConfig.getModelName())){
//            throw new BusinessException("模型为空");
//        }
//
//        // todo 目前硬编码模型服务商，后续需要根据不同的服务商进行发送消息
//        conversationDomainService.sendMessage(id, userId, conversationRequest.getMessage(),
//                modelConfig.getModelName());
    }
}
