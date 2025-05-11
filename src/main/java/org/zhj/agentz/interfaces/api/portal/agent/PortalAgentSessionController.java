package org.zhj.agentz.interfaces.api.portal.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.zhj.agentz.application.agent.service.AgentSessionAppService;
import org.zhj.agentz.application.conversation.dto.ChatRequest;
import org.zhj.agentz.application.conversation.service.ConversationAppService;
import org.zhj.agentz.domain.conversation.dto.MessageDTO;
import org.zhj.agentz.domain.conversation.dto.SessionDTO;
import org.zhj.agentz.infrastructure.auth.UserContext;
import org.zhj.agentz.interfaces.api.common.Result;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Agent会话管理
 */
@RestController
@RequestMapping("/agent/session")
public class PortalAgentSessionController {

    private final Logger logger = LoggerFactory.getLogger(PortalAgentSessionController.class);
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final AgentSessionAppService agentSessionAppService;
    private final ConversationAppService conversationAppService;

    public PortalAgentSessionController(AgentSessionAppService agentSessionAppService,
                                        ConversationAppService conversationAppService) {
        this.agentSessionAppService = agentSessionAppService;
        this.conversationAppService = conversationAppService;
    }


    /**
     * 获取会话中的消息列表
     *
     * @return
     */
   @GetMapping("/{sessionId}/messages")
   public Result<List<MessageDTO>> getConversationMessages(@PathVariable String sessionId) {
       String userId = UserContext.getCurrentUserId();
       return Result.success(conversationAppService.getConversationMessages(sessionId, userId));
   }


    /**
     * 获取助理会话列表
     *
     * @param agentId 助理id
     * @return 会话列表
     */
    @GetMapping("/{agentId}")
    public Result<List<SessionDTO>> getAgentSessionList(@PathVariable String agentId) {
        String userId = UserContext.getCurrentUserId();
        return Result.success(agentSessionAppService.getAgentSessionList(userId, agentId));
    }

    /**
     * 创建会话
     * 
     * @param agentId 助理id
     * @return 会话
     */
    @PostMapping("/{agentId}")
    public Result<SessionDTO> createSession(@PathVariable String agentId) {
        String userId = UserContext.getCurrentUserId();
        return Result.success(agentSessionAppService.createSession(userId, agentId));
    }

    /**
     * 更新会话
     * 
     * @param id    会话id
     * @param title 标题
     */
    @PutMapping("/{id}")
    public Result<Void> updateSession(@PathVariable String id, @RequestParam String title) {
        String userId = UserContext.getCurrentUserId();
        agentSessionAppService.updateSession(id, userId, title);
        return Result.success();
    }

    /**
     * 删除会话
     * 
     * @param id 会话id
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteSession(@PathVariable String id) {
        String userId = UserContext.getCurrentUserId();
        agentSessionAppService.deleteSession(id, userId);
        return Result.success();
    }

    /**
     * 发送消息
     * @param chatRequest 消息对象
     * @return
     */
    @PostMapping("/chat")
    public SseEmitter chat(@RequestBody @Validated ChatRequest chatRequest){
        return conversationAppService.chat(chatRequest, UserContext.getCurrentUserId());
    }
}
