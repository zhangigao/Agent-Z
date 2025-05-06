package org.zhj.agentz.infrastructure.converter;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.zhj.agentz.domain.task.constant.TaskStatus;

/**
 * 任务状态枚举转换器
 *
 * @Author 86155
 * @Date 2025/5/6
 */
@MappedTypes(TaskStatus.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class TaskStatusConverter extends JsonToStringConverter<TaskStatus> {

    public TaskStatusConverter() {
        super(TaskStatus.class);
    }
}
