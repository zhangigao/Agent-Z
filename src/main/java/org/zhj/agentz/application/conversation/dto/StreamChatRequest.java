package org.zhj.agentz.application.conversation.dto;

/**
 * 流式聊天请求DTO
 * @Author 86155
 * @Date 2025/5/5
 */
public class StreamChatRequest extends ChatRequest {

    /**
     * 是否启用流式响应
     */
    private boolean stream = true;

    public boolean isStream() {
        return stream;
    }

    public void setStream(boolean stream) {
        this.stream = stream;
    }
}
