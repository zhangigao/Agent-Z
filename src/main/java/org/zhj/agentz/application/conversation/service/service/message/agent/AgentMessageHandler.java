package org.zhj.agentz.application.conversation.service.service.message.agent;

import dev.langchain4j.service.tool.ToolProvider;
import org.springframework.stereotype.Component;
import org.zhj.agentz.application.conversation.service.service.message.AbstractMessageHandler;
import org.zhj.agentz.domain.conversation.service.MessageDomainService;
import org.zhj.agentz.infrastructure.llm.LLMServiceFactory;


/**
 * Agent消息处理器
 * 用于支持工具调用的对话模式
 * 实现任务拆分、执行和结果汇总的工作流
 * 使用事件驱动架构进行状态转换
 */
@Component(value = "agentMessageHandler")
public class AgentMessageHandler extends AbstractMessageHandler {

    private final AgentToolManager agentToolManager;

    public AgentMessageHandler(
            LLMServiceFactory llmServiceFactory,
            AgentToolManager agentToolManager,
            MessageDomainService messageDomainService) {
        super(llmServiceFactory, messageDomainService);
        this.agentToolManager = agentToolManager;
    }

    // todo xhy 从 agent 中获取
    @Override
    protected ToolProvider provideTools() {
        return agentToolManager.createToolProvider(
                agentToolManager.getAvailableTools());
    }
}