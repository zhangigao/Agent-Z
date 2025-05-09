package org.zhj.agentz.domain.agent.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.zhj.agentz.infrastructure.entity.BaseEntity;


/** Agent工作区实体类 用于记录用户添加到工作区的Agent */
@TableName(value = "agent_workspace")
public class AgentWorkspaceEntity extends BaseEntity {

    /** 主键ID */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /** Agent ID */
    @TableField("agent_id")
    private String agentId;

    /** 用户ID */
    @TableField("user_id")
    private String userId;

    /**
     * 模型id
     */
    @TableField("model_id")
    private String modelId;

    public AgentWorkspaceEntity() {
    }

    public AgentWorkspaceEntity(String agentId, String userId, String modelId) {
        this.agentId = agentId;
        this.userId = userId;
        this.modelId = modelId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }
}
