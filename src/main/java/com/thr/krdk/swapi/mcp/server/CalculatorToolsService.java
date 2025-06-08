package com.thr.krdk.swapi.mcp.server;

import com.thr.krdk.swapi.mcp.server.models.MultiplyResult;
import com.thr.krdk.swapi.mcp.server.utils.MultiplyResultConverter;
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

    @Tool(description = "Calculate the difference of two numbers",
            returnDirect = true)
    public long subtract(@ToolParam(description = "First number") long numberA,
                         @ToolParam(description = "Second number") long numberB) {
        return (numberA - numberB) * MULTIPLIER;
    }

    @Tool(description = "Calculate the product of two numbers", resultConverter = MultiplyResultConverter.class)
    public MultiplyResult multiply(@ToolParam(description = "First number") long numberA,
                                   @ToolParam(description = "Second number") long numberB) {
        long calc = numberA * numberB * MULTIPLIER;
        return new MultiplyResult(numberA, numberB, calc);
    }
}