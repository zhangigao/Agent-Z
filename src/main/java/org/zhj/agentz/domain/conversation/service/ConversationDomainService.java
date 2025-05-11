package org.zhj.agentz.domain.conversation.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zhj.agentz.domain.conversation.model.MessageEntity;
import org.zhj.agentz.domain.conversation.repository.MessageRepository;

import java.util.List;

/**
 * 对话服务实现
 */
@Service
public class ConversationDomainService {

    private final Logger logger = LoggerFactory.getLogger(ConversationDomainService.class);
    private final MessageRepository messageRepository;



    public ConversationDomainService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }



    /**
     * 获取会话中的消息列表
     *
     * @param sessionId 会话id
     * @return 消息列表
     */
    public List<MessageEntity> getConversationMessages(String sessionId) {
        return messageRepository.selectList(
                Wrappers.<MessageEntity>lambdaQuery().eq(MessageEntity::getSessionId, sessionId).orderByAsc(MessageEntity::getCreatedAt));
    }


    public void insertBathMessage(List<MessageEntity> messages){
        messageRepository.insert(messages);
    }

    public MessageEntity saveMessage(MessageEntity message){
        messageRepository.insert(message);
        return message;
    }

    /**
     * 删除会话下的消息
     *
     * @param sessionId 会话id
     */
    public void deleteConversationMessages(String sessionId) {
        messageRepository.delete(Wrappers.<MessageEntity>lambdaQuery().eq(MessageEntity::getSessionId, sessionId));
    }

    public void deleteConversationMessages(List<String> sessionIds) {
        messageRepository.checkedDelete(Wrappers.<MessageEntity>lambdaQuery().in(MessageEntity::getSessionId, sessionIds));
    }

    /**
     * 更新消息的token数量
     *
     * @param message 消息实体
     */
    @Transactional
    public void updateMessageTokenCount(MessageEntity message) {
        logger.info("更新消息token数量，消息ID: {}, token数量: {}", message.getId(), message.getTokenCount());
        messageRepository.checkedUpdateById(message);
    }
}