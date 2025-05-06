package org.zhj.agentz.domain.conversation.constant;

import org.zhj.agentz.infrastructure.exception.BusinessException;

/**
 * 提示测角色枚举
 *
 * @Author 86155
 * @Date 2025/5/6
 */
public enum Role {
    USER,SYSTEM,ASSISTANT;

    public static Role fromCode(String code) {
        for (Role role : values()) {
            if(role.name().equals(code)) {
                return  role;
            }
        }
        throw new BusinessException("Unknown model type code: " + code);
    }
}
