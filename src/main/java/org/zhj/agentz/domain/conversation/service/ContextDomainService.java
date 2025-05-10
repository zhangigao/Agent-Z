package org.zhj.agentz.domain.conversation.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import org.zhj.agentz.domain.conversation.model.ContextEntity;
import org.zhj.agentz.domain.conversation.repository.ContextRepository;
import org.zhj.agentz.infrastructure.exception.BusinessException;


@Service
public class ContextDomainService {

    private final ContextRepository contextRepository;

    public ContextDomainService(ContextRepository contextRepository) {
        this.contextRepository = contextRepository;
    }

    // 获取历史消息id
    public ContextEntity getBySessionId(String sessionId) {
        LambdaQueryWrapper<ContextEntity> wrapper = Wrappers.<ContextEntity>lambdaQuery()
                .eq(ContextEntity::getSessionId, sessionId)
                .select();
        ContextEntity contextEntity = contextRepository.selectOne(wrapper);
        if (contextEntity==null){
            throw new BusinessException("消息上下文不存在");
        }
        return contextEntity;
    }

    public ContextEntity findBySessionId(String sessionId) {
        LambdaQueryWrapper<ContextEntity> wrapper = Wrappers.<ContextEntity>lambdaQuery()
                .eq(ContextEntity::getSessionId, sessionId);
        return contextRepository.selectOne(wrapper);
    }


    public ContextEntity insertOrUpdate(ContextEntity contextEntity) {
        try {
            contextRepository.insertOrUpdate(contextEntity);
        }catch (Exception e){
            System.out.println(e);
        }
        return contextEntity;
    }
}
