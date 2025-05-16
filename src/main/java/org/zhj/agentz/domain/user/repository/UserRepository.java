package org.zhj.agentz.domain.user.repository;

import org.apache.ibatis.annotations.Mapper;
import org.zhj.agentz.domain.user.model.UserEntity;
import org.zhj.agentz.infrastructure.repository.MyBatisPlusExtRepository;


/** 模型仓储接口 */
@Mapper
public interface UserRepository extends MyBatisPlusExtRepository<UserEntity> {

}