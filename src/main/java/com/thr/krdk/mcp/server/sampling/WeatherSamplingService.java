package com.thr.krdk.mcp.server.sampling;

import io.modelcontextprotocol.spec.McpSchema;
import io.modelcontextprotocol.spec.McpSchema.CreateMessageResult;
import io.modelcontextprotocol.spec.McpSchema.LoggingLevel;
import io.modelcontextprotocol.spec.McpSchema.LoggingMessageNotification;
import io.modelcontextprotocol.spec.McpSchema.ModelPreferences;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.mcp.McpToolUtils;
import org.springframework.ai.model.ModelOptionsUtils;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.boot.actuate.endpoint.web.reactive.AdditionalHealthEndpointPathsWebFluxHandlerMapping;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.List;

/*
callMcpSampling şu adımları izler: ToolContext’ten MCP exchange’i alır,
“Start sampling” ve “Finish sampling” mesajlarıyla log tutar; sonra “You are a poet!” sistem prompt’u ve hava durumu
verisi içeren bir CreateMessageRequest hazırlar, önce “gemma”
sonra “qwen” hint’leriyle iki ayrı createMessage çağrısı yapar; gelen şiir metinlerini birleştirip orijinal JSON’la birlikte geri döner.
 */
@Service
// aşağıdaki sınfı MCPApplication'a eklemeliyiz
public class WeatherSamplingService {


    private final RestClient restClient;
    private final AdditionalHealthEndpointPathsWebFluxHandlerMapping managementHealthEndpointWebFluxHandlerMapping;

    public WeatherSamplingService(AdditionalHealthEndpointPathsWebFluxHandlerMapping managementHealthEndpointWebFluxHandlerMapping) {
        this.restClient = RestClient.create();
        this.managementHealthEndpointWebFluxHandlerMapping = managementHealthEndpointWebFluxHandlerMapping;
    }


    public record WeatherResponse(Current current) {
        public record Current(LocalDateTime time, int interval, double temperature_2m) {
        }
    }

    @Tool(description = "Get the temperature (in celsius) for a specific location")
    public String getTemperature(@ToolParam(description = "The location latitude") double latitude,
                                 @ToolParam(description = "The location longitude") double longitude,
                                 ToolContext toolContext) {

        WeatherResponse weatherResponse = restClient
                .get()
                .uri("https://api.open-meteo.com/v1/forecast?latitude={latitude}&longitude={longitude}&current=temperature_2m",
                        latitude, longitude)
                .retrieve()
                .body(WeatherResponse.class);

        String responseWithPoems = callMcpSampling(toolContext, weatherResponse);

        return responseWithPoems;
    }

    public String callMcpSampling(ToolContext toolContext, WeatherResponse weatherResponse) {

        StringBuilder qwenWeatherPoem = new StringBuilder();
        StringBuilder gemmaWeatherPoem = new StringBuilder();

        McpToolUtils.getMcpExchange(toolContext)
                .ifPresent(exchange -> {

                    exchange.loggingNotification(LoggingMessageNotification.builder()
                            .level(LoggingLevel.INFO)
                            .data("Start sampling")
                            .build());

                    if (exchange.getClientCapabilities().sampling() != null) {
                        var messageRequestBuilder = McpSchema.CreateMessageRequest.builder()
                                .systemPrompt("You are a poet!")
                                .messages(List.of(new McpSchema.SamplingMessage(McpSchema.Role.USER,
                                        new McpSchema.TextContent(
                                                "Please write a poem about thius weather forecast (temperature is in Celsious). Use markdown format :\n "
                                                        + ModelOptionsUtils
                                                        .toJsonStringPrettyPrinter(weatherResponse)))));

                        var managementHealthEndpointWebFluxHandlerMappingLlmMessageRequest = messageRequestBuilder
                                .modelPreferences(ModelPreferences.builder().addHint("openai").build())
                                .build();
                        CreateMessageResult openAiLlmResponse = exchange.createMessage(managementHealthEndpointWebFluxHandlerMappingLlmMessageRequest);

                        qwenWeatherPoem.append(((McpSchema.TextContent) openAiLlmResponse.content()).text());

                        var qwenLlmMessageRequest = messageRequestBuilder
                                .modelPreferences(ModelPreferences.builder().addHint("anthropic").build())
                                .build();
                        CreateMessageResult anthropicAiLlmResponse = exchange.createMessage(qwenLlmMessageRequest);

                        gemmaWeatherPoem.append(((McpSchema.TextContent) anthropicAiLlmResponse.content()).text());

                    }

                    exchange.loggingNotification(LoggingMessageNotification.builder()
                            .level(LoggingLevel.INFO)
                            .data("Finish Sampling")
                            .build());

                });

        String responseWithPoems = "Gemma poem about the weather: " + qwenWeatherPoem.toString() + "\n\n" +
                "Qwen poem about the weather: " + gemmaWeatherPoem.toString() + "\n"
                + ModelOptionsUtils.toJsonStringPrettyPrinter(weatherResponse);


        return responseWithPoems;

    }

}