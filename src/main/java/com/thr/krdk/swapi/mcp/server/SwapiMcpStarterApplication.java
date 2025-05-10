package com.thr.krdk.swapi.mcp.server;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SwapiMcpStarterApplication {

    private static final double RESOURCE_PRIORITY = 0.5; // Define a named constant for the magic number

    public static void main(String[] args) {
        SpringApplication.run(SwapiMcpStarterApplication.class, args);
    }

    @Bean
    public ToolCallbackProvider swapiTools(SwapiToolsService swapiToolsService, CalculatorToolsService calculatorToolsService, WhoAmIToolsService whoAmIToolsService) {
        return MethodToolCallbackProvider.builder().toolObjects(swapiToolsService, calculatorToolsService, whoAmIToolsService).build();
    }
}
