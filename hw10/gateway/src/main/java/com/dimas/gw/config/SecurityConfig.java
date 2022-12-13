package com.dimas.gw.config;

import com.dimas.gw.exception.handler.ExceptionResponseHandler;
import com.dimas.gw.filters.ReactiveRequestContextFilter;
import com.dimas.gw.security.CustomJwtAuthenticationConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;

import java.util.List;

import static com.dimas.gw.utils.Constants.GUEST_AUTHORITY;
import static com.dimas.gw.utils.Constants.USER_PERMISSION;
import static org.springframework.security.config.web.server.SecurityWebFiltersOrder.AUTHENTICATION;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String API_PROFILE = "/api/v1/users/**";//in theory should be protected by roles
    private static final String API_SYSTEM = "/api/v1/system";
    private static final String API_HOSTNAME = "/api/v1/hostname";
    private static final String API_REGISTER = "/api/v1/register";

    private static final String[] API_UNPROTECTED_PATHS = List.of(
            "/actuator/**"
    ).toArray(new String[0]);

    private static final String[] API_ANONYMOUS_PATHS = List.of(
            API_REGISTER
    ).toArray(new String[0]);

    private final ExceptionResponseHandler exceptionResponseHandler;
    private final CustomJwtAuthenticationConverter jwtAuthenticationConverter;

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .addFilterBefore(reactiveRequestContextFilter(), AUTHENTICATION)
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .logout().disable()
                .authorizeExchange()
                .pathMatchers(API_ANONYMOUS_PATHS).hasAnyAuthority(GUEST_AUTHORITY)
                .pathMatchers(API_UNPROTECTED_PATHS).permitAll()
                .pathMatchers(API_PROFILE).hasAnyAuthority(USER_PERMISSION)
                .pathMatchers(API_SYSTEM).hasAnyAuthority(USER_PERMISSION)
                .pathMatchers(API_HOSTNAME).hasAnyAuthority(USER_PERMISSION)
                .anyExchange().authenticated()
                .and()
                .anonymous().principal("guest").authorities(GUEST_AUTHORITY)
                .and()
                .oauth2ResourceServer()
                .authenticationEntryPoint(authenticationEntryPoint())
                .accessDeniedHandler(accessDeniedHandler())
                .jwt().jwtAuthenticationConverter(jwtAuthenticationConverter);
        return http.build();
    }

    @Bean
    protected ServerAuthenticationEntryPoint authenticationEntryPoint() {
        return (serverWebExchange, e) -> exceptionResponseHandler.exceptionToResponse(serverWebExchange, HttpStatus.UNAUTHORIZED, e);
    }

    @Bean
    protected ServerAccessDeniedHandler accessDeniedHandler() {
        return (serverWebExchange, e) -> exceptionResponseHandler.exceptionToResponse(serverWebExchange, HttpStatus.FORBIDDEN, e);
    }

    @Bean
    protected ErrorWebExceptionHandler errorWebExceptionHandler() {
        return (serverWebExchange, e) -> exceptionResponseHandler.exceptionToResponse(serverWebExchange, HttpStatus.BAD_REQUEST, e);
    }

    @Bean
    protected ReactiveRequestContextFilter reactiveRequestContextFilter() {
        return new ReactiveRequestContextFilter();
    }
}

