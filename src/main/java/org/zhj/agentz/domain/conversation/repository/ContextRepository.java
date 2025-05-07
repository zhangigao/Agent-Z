package org.zhj.agentz.domain.conversation.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.zhj.agentz.domain.conversation.model.ContextEntity;


/**
 * 上下文仓库接口
 */
@Mapper
public interface ContextRepository extends BaseMapper<ContextEntity> {
}