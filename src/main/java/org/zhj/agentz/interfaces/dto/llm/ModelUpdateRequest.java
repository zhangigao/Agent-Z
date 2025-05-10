package org.zhj.agentz.interfaces.dto.llm;


import jakarta.validation.constraints.NotBlank;
import org.zhj.agentz.domain.agent.model.LLMModelConfig;

/**
 * 模型更新请求
 */
public class ModelUpdateRequest {
    
    /**
     * 模型ID
     */
    private String id;

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
     * 模型配置
     */
    private LLMModelConfig config;

    /**
     * 模型状态
     */
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
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
    
    public LLMModelConfig getConfig() {
        return config;
    }
    
    public void setConfig(LLMModelConfig config) {
        this.config = config;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }
}