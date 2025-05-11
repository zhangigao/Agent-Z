package org.zhj.agentz.domain.task.repository;

import org.apache.ibatis.annotations.Mapper;
import org.zhj.agentz.domain.task.model.TaskEntity;
import org.zhj.agentz.infrastructure.repository.MyBatisPlusExtRepository;

/**
 * 任务仓储接口
 */
@Mapper
public interface TaskRepository extends MyBatisPlusExtRepository<TaskEntity> {
    

} 