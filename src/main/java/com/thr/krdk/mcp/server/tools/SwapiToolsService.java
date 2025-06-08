package com.thr.krdk.mcp.server.tools;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thr.krdk.mcp.server.utils.RestClientUtil;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class SwapiToolsService {
    private final RestClientUtil restClientUtil;

    public SwapiToolsService(RestClientUtil restClientUtil) {
        this.restClientUtil = restClientUtil;
    }

    @Tool(description = "Get Star Wars character information by ID")
    public String getStarWarsCharacter(@ToolParam(description = "Character ID") String id) {

        // Stadio tipindeki mcp server kesinlikle log'la işlemi yapılmamalı. SLF4J kullanılmalı.
        // System.out.println("swapi çağrıldı");  // stadioda bu satır aktif olursa  server ayağa kalkmaz

        String apiUrl = "https://www.swapi.tech/api/people/" + id;
        String jsonResponse = restClientUtil.executeGet(apiUrl, String.class);

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonResponse);
            JsonNode properties = root.path("result").path("properties");

            String name = properties.path("name").asText();
            String gender = properties.path("gender").asText();
            String birthYear = properties.path("birth_year").asText();

            return String.format("Name: %s - Gender: %s- Birth Year: %s", name, gender, birthYear);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error parsing Star Wars character data.";
        }

    }
}