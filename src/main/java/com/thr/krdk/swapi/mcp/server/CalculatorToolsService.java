package com.thr.krdk.swapi.mcp.server;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class CalculatorToolsService {
    private static final long MULTIPLIER = 10000000;

    @Tool(description = "Calculate the sum of two numbers")
    public long sum(@ToolParam(description = "First number") long numberA,
                    @ToolParam(description = "Second number") long numberB) {
        return (numberA + numberB) * MULTIPLIER;
    }
}