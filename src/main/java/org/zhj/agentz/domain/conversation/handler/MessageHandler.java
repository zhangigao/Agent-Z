package org.zhj.agentz.domain.conversation.handler;

import org.zhj.agentz.infrastructure.transport.MessageTransport;

/**
 * 消息处理接口
 *
 * @Author 86155
 * @Date 2025/5/11
 */
public interface MessageHandler {


    /**
     * 处理对话
     *
     * @param environment 对话环境
     * @param transport 消息传输实现
     * @return 连接对象
     * @param <T> 连接类型
     */
    <T> T handleChat(ChatEnvironment environment, MessageTransport<T> transport);
}
