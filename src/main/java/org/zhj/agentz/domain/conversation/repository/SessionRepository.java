package org.zhj.agentz.domain.conversation.repository;

import org.apache.ibatis.annotations.Mapper;
import org.zhj.agentz.domain.conversation.model.SessionEntity;
import org.zhj.agentz.infrastructure.repository.MyBatisPlusExtRepository;

/**
 * 会话仓库接口
 */
@Mapper
public interface SessionRepository extends MyBatisPlusExtRepository<SessionEntity> {
}