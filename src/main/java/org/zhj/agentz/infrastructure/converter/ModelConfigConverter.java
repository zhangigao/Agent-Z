package org.zhj.agentz.infrastructure.converter;

import org.apache.ibatis.type.MappedTypes;
import org.zhj.agentz.domain.agent.model.LLMModelConfig;


/**
 * 模型配置转换器
 */
@MappedTypes(LLMModelConfig.class)
public class ModelConfigConverter extends JsonToStringConverter<LLMModelConfig> {
    
    public ModelConfigConverter() {
        super(LLMModelConfig.class);
    }
} 