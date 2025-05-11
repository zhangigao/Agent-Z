package org.zhj.agentz.domain.task.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import org.zhj.agentz.domain.task.model.TaskAggregate;
import org.zhj.agentz.domain.task.model.TaskEntity;
import org.zhj.agentz.domain.task.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 任务领域服务
 *
 * @Author 86155
 * @Date 2025/5/11
 */
@Service
public class TaskDomainService {

    private final TaskRepository taskRepository;

    public TaskDomainService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    public TaskEntity addTask(TaskEntity taskEntity) {
        taskEntity.setStartTime(LocalDateTime.now());
        taskRepository.checkInsert(taskEntity);
        return taskEntity;
    }

    /**
     * 更新任务
     *
     * @param taskEntity 任务实体
     * @return 更新后的任务实体
     */
    public TaskEntity updateTask(TaskEntity taskEntity) {
        taskRepository.checkedUpdateById(taskEntity);
        return taskEntity;
    }

    /**
     * 获取当前会话的最新任务
     *
     * @param sessionId 会话ID
     * @return 任务实体
     */
    public TaskAggregate getCurrentSessionTask(String sessionId, String userId) {
        TaskEntity taskEntity = taskRepository.selectOne(Wrappers.<TaskEntity>lambdaQuery()
                .eq(TaskEntity::getSessionId, sessionId)
                .eq(TaskEntity::getUserId, userId)
                .eq(TaskEntity::getParentTaskId, "0")
                .orderByDesc(TaskEntity::getCreatedAt)
                .last("limit 1"));
        List<TaskEntity> subTasks = getSubTasks(taskEntity.getId());
        return new TaskAggregate(taskEntity, subTasks);
    }

    /**
     * 根据父任务id查出子任务
     * @param parentTaskId 父任务id
     * @return
     */
    public List<TaskEntity> getSubTasks(String parentTaskId) {
        LambdaQueryWrapper<TaskEntity> wrapper = Wrappers.<TaskEntity>lambdaQuery()
                .eq(TaskEntity::getParentTaskId, parentTaskId);
        return taskRepository.selectList(wrapper);
    }

    public void addAll(List<TaskEntity> taskEntities) {
        taskRepository.insert(taskEntities);
    }
}
