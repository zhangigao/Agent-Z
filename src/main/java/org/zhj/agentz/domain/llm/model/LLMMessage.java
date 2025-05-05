package org.zhj.agentz.domain.llm.model;

/**
 * LLM消息模型
 */
public class LLMMessage {
    
    /**
     * 消息角色
     */
    private String role;
    
    /**
     * 消息内容
     */
    private String content;
    
    public LLMMessage() {
    }
    
    public LLMMessage(String role, String content) {
        this.role = role;
        this.content = content;
    }

    public static LLMMessage ofUser(String content) {
        return new LLMMessage("user", content);
    }

    public static LLMMessage ofAssistant(String content) {
        return new LLMMessage("assistant", content);
    }

    public static LLMMessage ofSystem(String content) {
        return new LLMMessage("system", content);
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
}
