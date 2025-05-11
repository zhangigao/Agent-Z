package org.zhj.agentz.interfaces.api.portal.agent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zhj.agentz.application.task.service.TaskAppService;
import org.zhj.agentz.domain.task.model.TaskAggregate;
import org.zhj.agentz.infrastructure.auth.UserContext;
import org.zhj.agentz.interfaces.api.common.Result;

/**
 * agent任务管理
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskAppService taskAppService;

    @Autowired
    public TaskController(TaskAppService taskAppService) {
        this.taskAppService = taskAppService;
    }

    /**
     * 获取当前会话的任务
     * @param sessionId 会话id
     */
    @GetMapping("/session/{sessionId}/latest")
    public Result<TaskAggregate> getSessionTasks(@PathVariable String sessionId) {
        String userId = UserContext.getCurrentUserId();
        return Result.success(taskAppService.getCurrentSessionTask(sessionId,userId));
    }
}