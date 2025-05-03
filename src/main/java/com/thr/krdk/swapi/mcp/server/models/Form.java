package com.thr.krdk.swapi.mcp.server.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Form(long id, String formName, String description, long status) {
}
