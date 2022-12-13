package com.dimas.gw.security;

import com.dimas.gw.utils.ReactiveRequestContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

import static com.dimas.gw.utils.Constants.USER_ID_CLAIM;
import static com.dimas.gw.utils.Constants.USER_PERMISSION;
import static java.util.Objects.isNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomJwtAuthenticationConverter implements Converter<Jwt, Mono<AbstractOAuth2TokenAuthenticationToken<Jwt>>> {

    @Override
    public Mono<AbstractOAuth2TokenAuthenticationToken<Jwt>> convert(Jwt jwt) {
        return ReactiveRequestContextHolder.getRequest().flatMap(exchange -> {
            final var userId = jwt.getClaim(USER_ID_CLAIM);
            log.debug("jwt claim userid={}", userId);
//            TODO get user details by user id
            if (isNull(userId)) {
                return Mono.error(new InvalidBearerTokenException("No userId provided"));
            }
            final var authFromToken = new ArrayList<>(new JwtGrantedAuthoritiesConverter().convert(jwt));
            authFromToken.add(new SimpleGrantedAuthority(USER_PERMISSION));//but who care
            return Mono.just(new CustomJwtAuthenticationToken(jwt, "user", authFromToken));
        });
    }
}
