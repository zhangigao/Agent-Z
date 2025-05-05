package org.zhj.agentz.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.zhj.agentz.domain.llm.service.LLMService;
import org.zhj.agentz.infrastructure.integration.llm.siliconflow.SiliconFlowLlmService;


import java.util.HashMap;
import java.util.Map;

/**
 * LLM服务配置
 */
@Configuration
public class LLMConfig {
    
    @Value("${llm.provider.default}")
    private String defaultProvider;
    
    /**
     * 默认LLM服务
     */
    @Bean
    @Primary
    public LLMService defaultLlmService(SiliconFlowLlmService siliconFlowLlmService) {
        // 直接返回SiliconFlow服务作为默认服务
        return siliconFlowLlmService;
    }
    
    /**
     * LLM服务映射
     */
    @Bean
    public Map<String, LLMService> llmServiceMap(SiliconFlowLlmService siliconFlowLlmService) {
        Map<String, LLMService> serviceMap = new HashMap<>();
        // 确保键名与defaultProvider + "LlmService"匹配
        serviceMap.put("siliconflowLLMService", siliconFlowLlmService);
        return serviceMap;
    }
}
