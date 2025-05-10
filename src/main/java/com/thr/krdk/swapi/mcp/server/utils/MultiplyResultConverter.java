package com.thr.krdk.swapi.mcp.server.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.tool.execution.ToolCallResultConverter;
import org.springframework.lang.Nullable;

import java.lang.reflect.Type;

public class MultiplyResultConverter implements ToolCallResultConverter {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    @Nullable
    public String convert(@Nullable Object result, @Nullable Type returnType) {
        try {
            return MAPPER.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Error converting result to JSON", e);
        }
    }
}