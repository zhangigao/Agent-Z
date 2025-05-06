package org.zhj.agentz.infrastructure.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * MyBatis-Plus配置类
 * 用于配置MyBatis-Plus的自动填充等功能
 * @Author 86155
 * @Date 2025/5/6
 */
@Configuration
public class MybatisPlusConfig implements MetaObjectHandler {

    private static final Logger log = LoggerFactory.getLogger(MybatisPlusConfig.class);

    /** 插入操作自动填充 */
    @Override
    public void insertFill(MetaObject metaObject) {
        // 填充创建时间和更新时间
        LocalDateTime now = LocalDateTime.now();
        this.strictInsertFill(metaObject, "createdAt", LocalDateTime.class, now);
        this.strictInsertFill(metaObject, "updatedAt", LocalDateTime.class, now);
    }

    /** 更新操作自动填充 */
    @Override
    public void updateFill(MetaObject metaObject) {
        // 填充更新时间
        LocalDateTime now = LocalDateTime.now();
        this.strictUpdateFill(metaObject, "updatedAt", LocalDateTime.class, now);
    }
}
