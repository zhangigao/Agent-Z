package org.zhj.agentz.domain.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import org.zhj.agentz.application.agent.assembler.AgentAssembler;
import org.zhj.agentz.domain.agent.dto.AgentDTO;
import org.zhj.agentz.domain.agent.model.AgentEntity;
import org.zhj.agentz.domain.agent.model.AgentWorkspaceEntity;
import org.zhj.agentz.domain.agent.repository.AgentRepository;
import org.zhj.agentz.domain.agent.repository.AgentWorkspaceRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgentWorkspaceDomainService {

    private final AgentWorkspaceRepository agentWorkspaceRepository;

    private final AgentRepository agentRepository;

    public AgentWorkspaceDomainService(AgentWorkspaceRepository agentWorkspaceRepository,
                                       AgentDomainService agentServiceDomainService, AgentRepository agentRepository) {
        this.agentWorkspaceRepository = agentWorkspaceRepository;
        this.agentRepository = agentRepository;
    }

    public List<AgentDTO> getWorkspaceAgents(String userId) {

        LambdaQueryWrapper<AgentWorkspaceEntity> wrapper = Wrappers.<AgentWorkspaceEntity>lambdaQuery()
                .eq(AgentWorkspaceEntity::getUserId, userId).select(AgentWorkspaceEntity::getAgentId);

        List<String> agentIds = agentWorkspaceRepository.selectList(wrapper).stream()
                .map(AgentWorkspaceEntity::getAgentId).collect(Collectors.toList());

        if (agentIds.isEmpty()) {
            return Collections.emptyList();
        }
        List<AgentEntity> agents = agentRepository.selectBatchIds(agentIds);
        return agents.stream().map(AgentAssembler::toDTO).collect(Collectors.toList());
        
    }

    public boolean checkAgentWorkspaceExist(String agentId, String userId) {
        return agentWorkspaceRepository.checkAgentWorkspaceExist(agentId,userId);
    }

    public boolean deleteAgent(String agentId, String userId) {
        return agentWorkspaceRepository.delete(Wrappers.<AgentWorkspaceEntity>lambdaQuery()
                .eq(AgentWorkspaceEntity::getAgentId, agentId).eq(AgentWorkspaceEntity::getUserId, userId)) > 0;
    }
}
