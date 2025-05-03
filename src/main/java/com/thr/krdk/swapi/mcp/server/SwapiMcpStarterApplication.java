package com.thr.krdk.swapi.mcp.server;

import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Instant;
import java.util.List;

@SpringBootApplication
public class SwapiMcpStarterApplication {

    private static final double RESOURCE_PRIORITY = 0.5; // Define a named constant for the magic number

    public static void main(String[] args) {
        SpringApplication.run(SwapiMcpStarterApplication.class, args);
    }

    @Bean
    public ToolCallbackProvider swapiTools(SwapiToolsService swapiToolsService, CalculatorToolsService calculatorToolsService) {
        return MethodToolCallbackProvider.builder().toolObjects(swapiToolsService, calculatorToolsService).build();
    }

    // prompt
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

    @Bean
    public List<McpServerFeatures.SyncResourceSpecification> myResources() {
        McpSchema.Resource currentTimeResource = new McpSchema.Resource(
                "/currentTime",                                // URI
                "currentTime",                                 // Resource name
                "Current server time in ISO-8601 format",      // description
                "text/plain",                                  // MIME type
                new McpSchema.Annotations(
                        List.of(McpSchema.Role.USER),          // audience
                        RESOURCE_PRIORITY                      // priority: 0.0–1.0
                )
        );

        // 2) Associate the resource with a handler: generate the current time on each call
        McpServerFeatures.SyncResourceSpecification specification =
                new McpServerFeatures.SyncResourceSpecification(
                        currentTimeResource,
                        (exchange, request) -> {
                            String now = Instant.now().toString();
                            McpSchema.TextResourceContents contents =
                                    new McpSchema.TextResourceContents(
                                            request.uri(),   // incoming URI
                                            "text/plain",    // MIME type
                                            now              // contentts
                                    );
                            // Wrapping it with ReadResourceResult and returning it
                            return new McpSchema.ReadResourceResult(List.of(contents));
                        }
                );

        return List.of(specification);
    }

}
