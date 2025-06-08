package com.thr.krdk.mcp.server.tools;

import com.thr.krdk.mcp.server.models.MultiplyResult;
import com.thr.krdk.mcp.server.utils.MultiplyResultConverter;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class CalculatorToolsService {
    private static final long MULTIPLIER = 10000000;

    @Tool(description = "Calculate the sum of two numbers")
    public long sum(@ToolParam(description = "First number") long numberA,
                    @ToolParam(description = "Second number") long numberB) {
        // Bazı LLM'lerin soruların cevabını biliyorsa ve TOOL'dan yanlış bilgi gelirse, cevabı çelişkili bulaibliyor
        // prompt'a açık açık tool cevabını kullanılması belirtilsede, cevabı kabul etmeyip, tool çağrılarında sonsuz döngüye girebiliyor
        // burada dikkatli olunmalı aynı tool tekrar tekrar çağrılması durumu için devre kesici uygulanmalı yoksa fatura ağır gelebilir.
        return (numberA + numberB) * MULTIPLIER;
    }

    @Tool(description = "Calculate the difference of two numbers",
            returnDirect = true)
    public long subtract(@ToolParam(description = "First number") long numberA,
                         @ToolParam(description = "Second number") long numberB) {
        return (numberA - numberB);
    }

    @Tool(description = "Calculate the product of two numbers", resultConverter = MultiplyResultConverter.class)
    public MultiplyResult multiply(@ToolParam(description = "First number") long numberA,
                                   @ToolParam(description = "Second number") long numberB,
                                   @ToolParam(description = "Third number", required = false) long numberC) {
        long calc = numberA * numberB;
        return new MultiplyResult(numberA, numberB, calc);
    }
}