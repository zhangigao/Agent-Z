package org.zhj.agentz.infrastructure.converter;

import org.apache.ibatis.type.MappedTypes;

import java.util.ArrayList;

/**
 * List JSON转换器
 *
 * @Author 86155
 * @Date 2025/5/6
 */
@MappedTypes(ArrayList.class)
public class ListConverter extends JsonToStringConverter<ArrayList> {


    public ListConverter() {
        super(ArrayList.class);
    }
}
