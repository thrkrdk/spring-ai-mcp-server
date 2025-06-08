package com.thr.krdk.swapi.mcp.server.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.function.Function;

@Component
public class RestClientUtil {
    private final RestClient restClient;

    public RestClientUtil() {
        this.restClient = RestClient.builder()
                .build();
    }

    public <T> T executeGet(String uri, Class<T> responseType, Object... uriVariables) {
        RestClient.RequestHeadersUriSpec<?> requestSpec = restClient.get();
        RestClient.RequestHeadersSpec<?> headersSpec = getRequestHeadersSpec(uri, uriVariables, requestSpec);

        return headersSpec.retrieve().body(responseType);
    }

    private static RestClient.RequestHeadersSpec<?> getRequestHeadersSpec(String uri, Object[] uriVariables, RestClient.RequestHeadersUriSpec<?> requestSpec) {
        RestClient.RequestHeadersSpec<?> headersSpec;

        if (uriVariables == null || uriVariables.length == 0) {
            headersSpec = requestSpec.uri(uri);
        } else {
            headersSpec = requestSpec.uri(uri, uriVariables);
        }
        return headersSpec;
    }

    public <T> T executePost(String uri, Object body, Class<T> responseType, Object... uriVariables) {
        RestClient.RequestBodyUriSpec requestSpec = restClient.post();
        RestClient.RequestBodySpec bodySpec = getRequestBodySpec(uri, body, uriVariables, requestSpec);

        return bodySpec.retrieve().body(responseType);
    }
