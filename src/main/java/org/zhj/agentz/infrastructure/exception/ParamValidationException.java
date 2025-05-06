package org.zhj.agentz.infrastructure.exception;

/**
 * 参数校验异常类
 * @Author 86155
 * @Date 2025/5/6
 */
public class ParamValidationException extends BusinessException{

    private static final String DEFAULT_CODE = "PARAM_VALIDATION_ERROR";

    public ParamValidationException(String message) {
        super(DEFAULT_CODE, message);
    }

    public ParamValidationException(String paramName, String message) {
        super(DEFAULT_CODE, String.format("参数[%s]无效: %s", paramName, message));
    }
}
