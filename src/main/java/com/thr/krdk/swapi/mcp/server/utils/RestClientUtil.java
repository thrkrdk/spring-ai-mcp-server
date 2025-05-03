package com.thr.krdk.swapi.mcp.server.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.function.Function;

@Component
public class RestClientUtil {
    private final RestClient restClient;

    public RestClientUtil(
            @Value("${configcius.api.base-url}") String baseUrl,
            @Value("${configcius.api.access-token:}") String accessToken) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", accessToken)
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

    private static RestClient.RequestBodySpec getRequestBodySpec(String uri,
                                                                 Object body,
                                                                 Object[] uriVariables,
                                                                 RestClient.RequestBodyUriSpec requestSpec) {
        RestClient.RequestBodySpec bodySpec;

        if (uriVariables == null || uriVariables.length == 0) {
            bodySpec = requestSpec.uri(uri);
        } else {
            bodySpec = requestSpec.uri(uri, uriVariables);
        }

        if (body != null) {
            bodySpec.body(body);
        }
        return bodySpec;
    }

    public <T> T executePut(String uri, Object body, Class<T> responseType, Object... uriVariables) {
        RestClient.RequestBodyUriSpec requestSpec = restClient.put();
        RestClient.RequestBodySpec bodySpec = getRequestBodySpec(uri, body, uriVariables, requestSpec);

        return bodySpec.retrieve().body(responseType);
    }

    public <T> T executeDelete(String uri, Class<T> responseType, Object... uriVariables) {
        RestClient.RequestHeadersUriSpec<?> requestSpec = restClient.delete();
        RestClient.RequestHeadersSpec<?> headersSpec = getRequestHeadersSpec(uri, uriVariables, requestSpec);

        return headersSpec.retrieve().body(responseType);
    }

    public <T> T execute(Function<RestClient, RestClient.RequestBodyUriSpec> requestMethod,
                         String uri,
                         Object body,
                         Class<T> responseType,
                         Object... uriVariables) {
        RestClient.RequestBodyUriSpec requestSpec = requestMethod.apply(restClient);
        RestClient.RequestBodySpec bodySpec = getRequestBodySpec(uri, body, uriVariables, requestSpec);

        return bodySpec.retrieve().body(responseType);
    }
}