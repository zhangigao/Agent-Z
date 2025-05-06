package org.zhj.agentz.infrastructure.converter;

import org.apache.ibatis.type.MappedTypes;
import org.zhj.agentz.domain.llm.model.enums.ModelType;

/**
 * 模型类型转换器
 *
 * @Author 86155
 * @Date 2025/5/6
 */
@MappedTypes(ModelType.class)
public class ModelTypeConverter extends JsonToStringConverter<ModelType> {

    public ModelTypeConverter() {
        super(ModelType.class);
    }
}
