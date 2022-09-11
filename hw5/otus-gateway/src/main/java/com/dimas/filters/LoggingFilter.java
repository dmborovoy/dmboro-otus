package com.dimas.filters;

import com.dimas.config.prop.HttpLoggingProps;
import com.dimas.utils.RequestLogger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoggingFilter implements WebFilter {

    private final HttpLoggingProps httpLoggingProps;
    private final RequestLogger requestLogger;
    private final AntPathMatcher antPathMatcher;

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        if (!httpLoggingProps.getEnabled() || httpLoggingProps.getExcluded().stream()
                .anyMatch(el -> antPathMatcher.match(el, serverWebExchange.getRequest().getPath().toString()))) {
            return webFilterChain.filter(serverWebExchange);
        }
        log.info(requestLogger.getRequestMessage(serverWebExchange));
        return webFilterChain.filter(serverWebExchange).doFinally((tmp) ->
                log.info(requestLogger.getResponseMessage(serverWebExchange)));
    }
}
