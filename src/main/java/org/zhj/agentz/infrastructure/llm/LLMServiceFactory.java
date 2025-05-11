package org.zhj.agentz.infrastructure.llm;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import org.springframework.stereotype.Component;
import org.zhj.agentz.domain.llm.model.ModelEntity;
import org.zhj.agentz.domain.llm.model.ProviderEntity;
import org.zhj.agentz.infrastructure.llm.config.ProviderConfig;



/**
 * LLM服务工厂，用于创建LLM客户端
 *
 * @Author 86155
 * @Date 2025/5/11
 */
@Component
public class LLMServiceFactory {

    /**
     * 获取流式LLM客户端
     *
     * @param provider 服务商实体
     * @param model 模型实体
     * @return 流式聊天语言模型
     */
    public StreamingChatLanguageModel getStreamingClient(ProviderEntity provider, ModelEntity model) {
        org.zhj.agentz.domain.llm.model.config.ProviderConfig config = provider.getConfig();

        ProviderConfig providerConfig = new ProviderConfig(
                config.getApiKey(),
                config.getBaseUrl(),
                model.getModelId(),
                provider.getProtocol());

        return LLMProviderService.getStream(provider.getProtocol(), providerConfig);
    }

    /**
     * 获取标准LLM客户
     * @param provider 服务商实体
     * @param model 模型实体
     * @return 流式聊天语言模型
     */
    public ChatLanguageModel getStrandClient(ProviderEntity provider, ModelEntity model) {
        org.zhj.agentz.domain.llm.model.config.ProviderConfig config = provider.getConfig();

        ProviderConfig providerConfig = new ProviderConfig(
                config.getApiKey(),
                config.getBaseUrl(),
                model.getModelId(),
                provider.getProtocol());

        return LLMProviderService.getStrand(provider.getProtocol(), providerConfig);
    }

}
