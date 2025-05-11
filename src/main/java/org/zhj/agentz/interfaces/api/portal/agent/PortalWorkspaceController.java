package org.zhj.agentz.interfaces.api.portal.agent;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.zhj.agentz.application.agent.dto.AgentDTO;
import org.zhj.agentz.application.agent.service.AgentWorkspaceAppService;
import org.zhj.agentz.domain.agent.model.LLMModelConfig;
import org.zhj.agentz.infrastructure.auth.UserContext;
import org.zhj.agentz.interfaces.api.common.Result;
import org.zhj.agentz.interfaces.dto.agent.request.UpdateModelConfigRequest;

import java.util.List;

/**
 * Agent工作区
 */
@RestController
@RequestMapping("/agent/workspace")
public class PortalWorkspaceController {

    private final AgentWorkspaceAppService agentWorkspaceAppService;

    public PortalWorkspaceController(AgentWorkspaceAppService agentWorkspaceAppService) {
        this.agentWorkspaceAppService = agentWorkspaceAppService;
    }

    /**
     * 获取工作区下的助理
     * 
     * @return
     */
    @GetMapping("/agents")
    public Result<List<AgentDTO>> getAgents() {
        String userId = UserContext.getCurrentUserId();
        return Result.success(agentWorkspaceAppService.getAgents(userId));
    }


    /**
     * 删除工作区中的助理
     * 
     * @param id 助理id
     */
    @DeleteMapping("/agents/{id}")
    public Result<Void> deleteAgent(@PathVariable String id) {
        String userId = UserContext.getCurrentUserId(); 
        agentWorkspaceAppService.deleteAgent(id,userId);
        return Result.success();
    }

    /**
     * 设置agent的模型配置
     * @param config 模型配置
     * @param agentId agentId
     * @return
     */
    @PutMapping("/{agentId}/model/config")
    public Result<Void> saveModelConfig(@RequestBody @Validated UpdateModelConfigRequest config, @PathVariable String agentId){
        String userId = UserContext.getCurrentUserId();
        agentWorkspaceAppService.updateModelConfig(agentId, userId, config);
        return Result.success();
    }

    /**
     * 根据agentId和userId获取对应的modelId
     * @param agentId agentId
     * @return
     */
    @GetMapping("/{agentId}/model-config")
    public Result<LLMModelConfig> getConfiguredModelId(@PathVariable String agentId){
        String userId = UserContext.getCurrentUserId();
        return Result.success(agentWorkspaceAppService.getConfiguredModelId(agentId,userId));
    }
}