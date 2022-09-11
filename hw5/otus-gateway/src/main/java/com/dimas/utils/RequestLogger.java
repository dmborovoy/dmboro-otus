package com.dimas.utils;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import java.util.Objects;

@Service
public class RequestLogger {

    private static final String REQUEST_LOG_TEMPLATE = "Before request [%s %s headers={%s}]";
    private static final String RESPONSE_LOG_TEMPLATE = "After request [%s %s status=(%s) headers={%s}]";

    public String getRequestMessage(ServerWebExchange exchange) {
        final var request = exchange.getRequest();
        final var headers = Utils.mapToString(request.getHeaders().toSingleValueMap());
        final var method = request.getMethod();
        final var path = request.getURI().getPath();
        return String.format(REQUEST_LOG_TEMPLATE, method, path, headers);
    }

    public String getResponseMessage(ServerWebExchange exchange) {
        final var request = exchange.getRequest();
        final var response = exchange.getResponse();
        final var headers = Utils.mapToString(response.getHeaders().toSingleValueMap());
        final var method = request.getMethod();
        final var path = request.getURI().getPath();
        final var status = Objects.requireNonNull(response.getStatusCode()).toString();
        return String.format(RESPONSE_LOG_TEMPLATE, method, path, status, headers);
    }
}
