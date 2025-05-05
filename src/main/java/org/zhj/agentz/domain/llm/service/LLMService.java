package org.zhj.agentz.domain.llm.service;

import org.zhj.agentz.domain.llm.model.LLMRequest;
import org.zhj.agentz.domain.llm.model.LLMResponse;

/**
 * LLM服务接口
 */
public interface LLMService {
    
    /**
     * 发送请求到LLM服务商
     *
     * @param request LLM请求
     * @return LLM响应
     */
    LLMResponse chat(LLMRequest request);
    
    /**
     * 发送简单的文本请求
     *
     * @param text 用户输入文本
     * @return 生成的响应内容
     */
    String simpleChat(String text);
    
    /**
     * 获取服务商名称
     *
     * @return 服务商名称
     */
    String getProviderName();
    
    /**
     * 获取默认模型名称
     *
     * @return 默认模型名称
     */
    String getDefaultModel();
}
