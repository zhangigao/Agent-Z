package org.zhj.agentz.domain.llm.repository;

import org.apache.ibatis.annotations.Mapper;
import org.zhj.agentz.domain.llm.model.ModelEntity;
import org.zhj.agentz.infrastructure.repository.MyBatisPlusExtRepository;


/**
 * 模型仓储接口
 */
@Mapper
public interface ModelRepository extends MyBatisPlusExtRepository<ModelEntity> {
    
   
} 