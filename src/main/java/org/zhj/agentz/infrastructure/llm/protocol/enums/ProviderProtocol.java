package org.zhj.agentz.infrastructure.llm.protocol.enums;

import org.zhj.agentz.infrastructure.exception.BusinessException;

/**
 * 服务商协议枚举类
 *
 * @Author 86155
 * @Date 2025/5/6
 */
public enum ProviderProtocol {

    OpenAI, ANTHROPIC;

    public static ProviderProtocol fromCode(String code) {
        for (ProviderProtocol protocol : values()) {
            if (protocol.name().equals(code)) {
                return protocol;
            }
        }
        throw new BusinessException("Unknown model type code: " + code);
    }
}
