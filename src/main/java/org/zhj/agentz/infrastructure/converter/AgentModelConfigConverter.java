package org.zhj.agentz.infrastructure.converter;

import org.apache.ibatis.type.MappedTypes;
import org.zhj.agentz.domain.agent.model.AgentModelConfig;


/**
 * 模型配置转换器
 */
@MappedTypes(AgentModelConfig.class)
public class AgentModelConfigConverter extends JsonToStringConverter<AgentModelConfig> {

    public AgentModelConfigConverter() {
        super(AgentModelConfig.class);
    }
} 