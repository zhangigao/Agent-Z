package org.zhj.agentz.infrastructure.converter;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.zhj.agentz.domain.conversation.constant.MessageType;

/**
 * 消息类型枚举转换器
 *
 * @Author 86155
 * @Date 2025/5/6
 */
@MappedTypes(MessageType.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class MessageTypeConverter extends JsonToStringConverter<MessageType> {

    public MessageTypeConverter() {
        super(MessageType.class);
    }
}
