package org.zhj.agentz.domain.conversation.model;

import com.baomidou.mybatisplus.annotation.*;
import org.zhj.agentz.domain.conversation.constant.MessageType;
import org.zhj.agentz.domain.conversation.constant.Role;
import org.zhj.agentz.infrastructure.converter.MessageTypeConverter;
import org.zhj.agentz.infrastructure.entity.BaseEntity;

import java.time.LocalDateTime;
import java.util.Objects;

/** 消息实体类，代表对话中的一条消息 */
@TableName("messages")
public class MessageEntity extends BaseEntity {

    /** 消息唯一ID */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /** 所属会话ID */
    @TableField("session_id")
    private String sessionId;

    /** 消息角色 (user, assistant, system) */
    //@TableField(value = "role", typeHandler = RoleConverter.class)
    private Role role;

    /** 消息内容 */
    @TableField("content")
    private String content;

    /** 消息类型 */
    @TableField(value = "message_type", typeHandler = MessageTypeConverter.class)
    private MessageType messageType = MessageType.TEXT;

    /** 创建时间 */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /** Token数量 */
    @TableField("token_count")
    private Integer tokenCount = 0;

    /** 服务提供商 */
    @TableField("provider")
    private String provider;

    /** 使用的模型 */
    @TableField("model")
    private String model;

    /** 消息元数据 */
    @TableField("metadata")
    private String metadata;

    /** 无参构造函数 */
    public MessageEntity() {
    }

    // Getter和Setter方法
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getTokenCount() {
        return tokenCount;
    }

    public void setTokenCount(Integer tokenCount) {
        this.tokenCount = tokenCount;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public boolean isUserMessage() {
        return Objects.equals(this.role, Role.USER.name());
    }

    public boolean isAIMessage() {
        return Objects.equals(this.role, Role.ASSISTANT.name());
    }

    public boolean isSystemMessage() {
        return Objects.equals(this.role, Role.SYSTEM.name());
    }

    /**
     * 创建用户消息
     */
    public static MessageEntity createUserMessage(String sessionId, String content) {
        MessageEntity message = new MessageEntity();
        message.setSessionId(sessionId);
        message.setRole(Role.USER);
        message.setContent(content);
        message.setCreatedAt(LocalDateTime.now());
        return message;
    }

    /**
     * 创建助手消息
     */
    public static MessageEntity createAssistantMessage(String sessionId, String content,
                                                       String provider, String model, Integer tokenCount) {
        MessageEntity message = new MessageEntity();
        message.setSessionId(sessionId);
        message.setRole(Role.ASSISTANT);
        message.setContent(content);
        message.setCreatedAt(LocalDateTime.now());
        message.setProvider(provider);
        message.setModel(model);
        message.setTokenCount(tokenCount);
        return message;
    }
}