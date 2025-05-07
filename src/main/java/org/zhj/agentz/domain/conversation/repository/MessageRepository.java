package org.zhj.agentz.domain.conversation.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.zhj.agentz.domain.conversation.model.MessageEntity;

import java.util.List;

/**
 * 消息仓库接口
 */
@Mapper
public interface MessageRepository extends BaseMapper<MessageEntity> {
    List<MessageEntity> selectList(LambdaQueryWrapper<MessageEntity> eq);
}