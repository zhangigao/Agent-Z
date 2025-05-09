package org.zhj.agentz.domain.llm.repository;

import org.apache.ibatis.annotations.Mapper;
import org.zhj.agentz.domain.llm.model.ProviderEntity;
import org.zhj.agentz.infrastructure.repository.MyBatisPlusExtRepository;


/**
 * 服务提供商仓储接口
 */
@Mapper
public interface ProviderRepository extends MyBatisPlusExtRepository<ProviderEntity> {

}