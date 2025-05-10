package org.zhj.agentz.interfaces.dto.llm;

import jakarta.validation.constraints.NotBlank;
import org.zhj.agentz.domain.agent.model.LLMModelConfig;
import org.zhj.agentz.domain.llm.model.enums.ModelType;

/**
 * 模型创建请求
 */
public class ModelCreateRequest {
    
    /**
     * 服务商ID
     */
    private String providerId;

    /**
     * 模型id
     */
    @NotBlank(message = "模型id不可为空")
    private String modelId;

    /**
     * 模型名称
     */
    @NotBlank(message = "名称不可为空")
    private String name;

    /**
     * 模型描述
     */
    private String description;

    /**
     * 模型类型
     */
    private ModelType type;

    /**
     * 模型配置
     */
    private LLMModelConfig config;
    
    public String getProviderId() {
        return providerId;
    }
    
    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
    
    public String getModelId() {
        return modelId;
    }
    
    public void setModelId(String modelId) {
        this.modelId = modelId;
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
    
    public ModelType getType() {
        return type;
    }
    
    public void setType(ModelType type) {
        this.type = type;
    }
    
    public LLMModelConfig getConfig() {
        return config;
    }
    
    public void setConfig(LLMModelConfig config) {
        this.config = config;
    }

}