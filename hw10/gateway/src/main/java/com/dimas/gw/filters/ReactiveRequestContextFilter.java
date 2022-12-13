package com.dimas.gw.filters;

import com.dimas.gw.utils.ReactiveRequestContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
public class ReactiveRequestContextFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange)
                .contextWrite((context -> context.put(ReactiveRequestContextHolder.CONTEXT_KEY, exchange.getRequest())));
    }
}
