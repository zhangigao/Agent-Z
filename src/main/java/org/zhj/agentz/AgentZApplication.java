package org.zhj.agentz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 启动类
 */
@SpringBootApplication
public class AgentZApplication {

    public static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AgentZApplication.class, args);
    }

    public static ConfigurableApplicationContext getContext() {
        return context;
    }
}
