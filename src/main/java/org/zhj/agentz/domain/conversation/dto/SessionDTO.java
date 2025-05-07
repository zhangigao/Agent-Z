package org.zhj.agentz.domain.conversation.dto;

import java.time.LocalDateTime;

/**
 * 会话DTO，用于API响应
 */
public class SessionDTO {
    private String id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isArchived;
    private String agentId;

    /**
     * 无参构造函数
     */
    public SessionDTO() {
    }

    /**
     * 全参构造函数
     */
    public SessionDTO(String id, String title, String description,
            LocalDateTime createdAt, LocalDateTime updatedAt,
            boolean isArchived, String agentVersionId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isArchived = isArchived;
        this.agentId = agentVersionId;
    }

    // Getter和Setter方法
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentVersionId) {
        this.agentId = agentVersionId;
    }


}