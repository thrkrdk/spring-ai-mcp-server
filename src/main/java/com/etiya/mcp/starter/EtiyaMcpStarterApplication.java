package com.etiya.mcp.starter;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EtiyaMcpStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(EtiyaMcpStarterApplication.class, args);
    }

    @Bean
    public ToolCallbackProvider configciusTools(ToolsService toolsService) {
        return MethodToolCallbackProvider.builder().toolObjects(toolsService).build();
    }


}
