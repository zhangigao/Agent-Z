package org.zhj.agentz.domain.llm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.zhj.agentz.domain.llm.model.config.ProviderConfig;
import org.zhj.agentz.infrastructure.converter.ProviderConfigConverter;
import org.zhj.agentz.infrastructure.converter.ProviderProtocolConverter;
import org.zhj.agentz.infrastructure.entity.BaseEntity;
import org.zhj.agentz.infrastructure.exception.BusinessException;
import org.zhj.agentz.infrastructure.llm.protocol.enums.ProviderProtocol;

/** 服务提供商领域模型 */
@TableName("providers")
public class ProviderEntity extends BaseEntity {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String userId;

    @TableField(typeHandler = ProviderProtocolConverter.class)
    private ProviderProtocol protocol;
    private String name;
    private String description;

    @TableField(typeHandler = ProviderConfigConverter.class)
    private ProviderConfig config;

    private Boolean isOfficial;
    private Boolean status;

    public void setConfig(ProviderConfig config) {
        this.config = config;
    }

    public ProviderConfig getConfig() {
        return config;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ProviderProtocol getProtocol() {
        return protocol;
    }

    public void setProtocol(ProviderProtocol protocol) {
        this.protocol = protocol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsOfficial() {
        return isOfficial;
    }

    public void setIsOfficial(Boolean official) {
        isOfficial = official;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void isActive() {
        if (!status) {
            throw new BusinessException("服务商未激活");
        }
    }
}