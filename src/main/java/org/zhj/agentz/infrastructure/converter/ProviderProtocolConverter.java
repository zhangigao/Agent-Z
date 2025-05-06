package org.zhj.agentz.infrastructure.converter;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.zhj.agentz.infrastructure.llm.protocol.enums.ProviderProtocol;

/**
 * 服务商协议转换器
 *
 * @Author 86155
 * @Date 2025/5/6
 */
@MappedTypes(ProviderProtocol.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class ProviderProtocolConverter extends JsonToStringConverter<ProviderProtocol> {

    public ProviderProtocolConverter(){
        super(ProviderProtocol.class);
    }
}
