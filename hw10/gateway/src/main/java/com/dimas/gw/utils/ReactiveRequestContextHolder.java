package com.dimas.gw.utils;

import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

public class ReactiveRequestContextHolder {
    private ReactiveRequestContextHolder() {}

    public static final Class<ServerHttpRequest> CONTEXT_KEY = ServerHttpRequest.class;

    public static Mono<ServerHttpRequest> getRequest() {
        return Mono.deferContextual(ctx -> Mono.just(ctx.get(CONTEXT_KEY)));
    }
}
