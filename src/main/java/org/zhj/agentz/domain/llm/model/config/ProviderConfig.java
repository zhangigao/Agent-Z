package org.zhj.agentz.domain.llm.model.config;

import java.io.Serializable;

/**
 * 服务商配置
 * @Author 86155
 * @Date 2025/5/6
 */
public class ProviderConfig implements Serializable {

    private String apiKey;
    private String baseUrl;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}

