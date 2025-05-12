package org.zhj.agentz.application.conversation.service.service.message;

import dev.langchain4j.service.TokenStream;

public interface Agent {
        TokenStream chat(String message);
    }
