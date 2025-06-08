package com.thr.krdk.mcp.server;

import com.logaritex.mcp.spring.SpringAiMcpAnnotationProvider;
import com.thr.krdk.mcp.server.prompts.PromptProvider;
import com.thr.krdk.mcp.server.tools.CalculatorToolsService;
import com.thr.krdk.mcp.server.tools.SwapiToolsService;
import com.thr.krdk.mcp.server.tools.WhoAmIToolsService;
import io.modelcontextprotocol.server.McpServerFeatures.SyncPromptSpecification;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;


@SpringBootApplication
public class McpStarterApplication {

    private static final double RESOURCE_PRIORITY = 0.5; // Define a named constant for the magic number

    public static void main(String[] args) {
        SpringApplication.run(McpStarterApplication.class, args);
    }

    @Bean
    public ToolCallbackProvider swapiTools(SwapiToolsService swapiToolsService, CalculatorToolsService calculatorToolsService, WhoAmIToolsService whoAmIToolsService) {
        return MethodToolCallbackProvider.builder().toolObjects(swapiToolsService, calculatorToolsService, whoAmIToolsService).build();
    }

    @Bean
    public List<SyncPromptSpecification> promptSpecs(PromptProvider promptProvider) {
        return SpringAiMcpAnnotationProvider.createSyncPromptSpecifications(List.of(promptProvider));
    }

    /*
    @Bean
    public List<McpServerFeatures.SyncPromptSpecification> starWarsCharacterPrompts() {
        // Prompt description: Id, description and list of arguments
        var prompt = new McpSchema.Prompt(
                "starWarsCharacterById",                                  // prompt name
                "Fetch a Star Wars character by ID using the SWAPI",      // prompt description
                List.of(new McpSchema.PromptArgument(
                        "characterId",                                        // name of the argument
                        "ID of the Star Wars character to retrieve",          // description for the argument
                        true                                                  // mandatory
                ))
        );

        // Prompt handler: creates a user message based on the incoming character ID.:
        var promptSpecification = new McpServerFeatures.SyncPromptSpecification(
                prompt,
                (exchange, getPromptRequest) -> {
                    String id = (String) getPromptRequest.arguments().get("characterId");
                    // Create a user message with the character ID. This is default prompt. Client will use this prompt to get the character details.
                    var userMessage = new McpSchema.PromptMessage(
                            McpSchema.Role.USER,
                            new McpSchema.TextContent(String.format(
                                    "Please return all details of the character with ID \"%s\" using the Star Wars API (SWAPI) — name, birth date, height, mass, list of films, etc. — in JSON format.",
                                    id
                            ))
                    );
                    return new McpSchema.GetPromptResult(
                            "Retrieve detailed Star Wars character information in JSON format",
                            List.of(userMessage)
                    );
                }
        );

        return List.of(promptSpecification);
    }
    */
}
