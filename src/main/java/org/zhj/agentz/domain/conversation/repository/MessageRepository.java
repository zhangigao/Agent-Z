package org.zhj.agentz.domain.conversation.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.zhj.agentz.domain.conversation.model.MessageEntity;
import org.zhj.agentz.infrastructure.repository.MyBatisPlusExtRepository;

import java.util.List;

/**
 * 消息仓库接口
 */
@Mapper
public interface MessageRepository extends MyBatisPlusExtRepository<MessageEntity> {
    List<MessageEntity> selectList(LambdaQueryWrapper<MessageEntity> eq);
}