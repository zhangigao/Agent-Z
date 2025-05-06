package org.zhj.agentz.domain.llm.model.enums;

import org.zhj.agentz.infrastructure.exception.BusinessException;

/**
 * 模型类型枚举
 *
 * @Author 86155
 * @Date 2025/5/6
 */
public enum ModelType {

    CHAT("CHAT", "对话模型"), EMBEDDING("EMBEDDING", "嵌入模型");

    private final String code;
    private final String description;

    ModelType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static ModelType fromCode(String code) {
        for (ModelType type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new BusinessException("Unknown model type code: " + code);
    }

}
