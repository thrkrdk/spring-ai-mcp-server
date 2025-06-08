package com.thr.krdk.mcp.server.services;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class WhoAmIToolsService {

    @Tool(description = "To get User Information")
    public String whoAmI() {
        return "{\"name\":\"Tahir\" ,\"lastName\":\"Kardak\", \"title\":\"Software Developer\"}";
    }
}