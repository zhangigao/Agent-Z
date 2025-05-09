package org.zhj.agentz.domain.agent.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.zhj.agentz.domain.agent.model.AgentEntity;
import org.zhj.agentz.infrastructure.repository.MyBatisPlusExtRepository;

/**
 * Agent仓库接口
 */
@Mapper
public interface AgentRepository extends MyBatisPlusExtRepository<AgentEntity> {
} 