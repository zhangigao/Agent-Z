package org.zhj.agentz.infrastructure.converter;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.zhj.agentz.domain.conversation.constant.Role;

/**
 * 提示测角色枚举转换器
 *
 * @Author 86155
 * @Date 2025/5/6
 */
@MappedTypes(Role.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class RoleConverter extends JsonToStringConverter<Role>{

    public RoleConverter() {
        super(Role.class);
    }
}
