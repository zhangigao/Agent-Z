package org.zhj.agentz.infrastructure.llm.protocol;


import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import org.springframework.stereotype.Service;
import org.zhj.agentz.infrastructure.llm.config.ProviderConfig;
import org.zhj.agentz.infrastructure.llm.factory.LLMProviderFactory;
import org.zhj.agentz.infrastructure.llm.protocol.enums.ProviderProtocol;

@Service
public class LLMProviderService {


    public ChatLanguageModel getNormal(ProviderProtocol protocol, ProviderConfig providerConfig){
        return LLMProviderFactory.getLLMProvider(protocol, providerConfig);
    }


    public StreamingChatLanguageModel getStream(ProviderProtocol protocol, ProviderConfig providerConfig){
        return LLMProviderFactory.getLLMProviderByStream(protocol, providerConfig);
    }
}
