package com.dimas.filters;

import com.dimas.security.CustomJwtAuthenticationToken;
import com.dimas.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.dimas.utils.Constants.USER_ID_CLAIM;
import static com.dimas.utils.Constants.USER_NAME_CLAIM;

@Slf4j
@Component
public class UserAuthenticationDetailsFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(CustomJwtAuthenticationToken.class::isInstance)
                .cast(CustomJwtAuthenticationToken.class)
                .map(token -> {
                    setHeadersFromToken(token.getToken(), exchange);
                    return token;
                })
                .then(chain.filter(exchange));
    }

    private void setHeadersFromToken(Jwt token, ServerWebExchange exchange) {
        final var mutableRequest = exchange.getRequest().mutate();
        final String userIdClaim = token.getClaim(USER_ID_CLAIM);
        final String userNameClaim = token.getClaim(USER_NAME_CLAIM);
        log.info("Claims to Headers: {}={}; {}={}", USER_ID_CLAIM, userIdClaim, USER_NAME_CLAIM, userNameClaim);
        mutableRequest.header(Constants.USER_ID_HEADER_NAME, userIdClaim);
        mutableRequest.header(Constants.USER_NAME_HEADER_NAME, userNameClaim);
    }

}
