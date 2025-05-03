package com.thr.krdk.swapi.mcp.server;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SwapiMcpStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwapiMcpStarterApplication.class, args);
    }

    @Bean
    public ToolCallbackProvider configciusTools(ToolsService toolsService) {
        return MethodToolCallbackProvider.builder().toolObjects(toolsService).build();
    }


}
