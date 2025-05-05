package org.zhj.agentz.application.conversation.dto;

/**
 * 流式聊天响应DTO
 * @Author 86155
 * @Date 2025/5/5
 */
public class StreamChatResponse extends ChatResponse{

    /**
     * 是否响应完毕
     */
    private boolean done;

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
