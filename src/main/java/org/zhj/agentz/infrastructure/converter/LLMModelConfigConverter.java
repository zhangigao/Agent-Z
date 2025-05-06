package org.zhj.agentz.infrastructure.converter;

import org.apache.ibatis.type.MappedTypes;
import org.zhj.agentz.domain.agent.model.LLMModelConfig;

/**
 * LLMModelConfig JSON转换器
 *
 * @Author 86155
 * @Date 2025/5/6
 */
@MappedTypes(LLMModelConfig.class)
public class LLMModelConfigConverter extends JsonToStringConverter<LLMModelConfig> {

    public LLMModelConfigConverter() {
        super(LLMModelConfig.class);
    }
}
