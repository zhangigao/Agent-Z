package org.zhj.agentz.application.conversation.service.service.message.chat;

import org.springframework.stereotype.Component;
import org.zhj.agentz.application.conversation.service.service.message.AbstractMessageHandler;
import org.zhj.agentz.domain.conversation.service.MessageDomainService;
import org.zhj.agentz.infrastructure.llm.LLMServiceFactory;


/**
 * 标准消息处理器
 */
@Component(value = "chatMessageHandler")
public class ChatMessageHandler extends AbstractMessageHandler {

    public ChatMessageHandler(
            LLMServiceFactory llmServiceFactory,
            MessageDomainService messageDomainService) {
        super(llmServiceFactory, messageDomainService);
    }
}