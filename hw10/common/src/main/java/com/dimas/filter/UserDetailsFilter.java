package com.dimas.filter;

import com.dimas.common.ExceptionResponseHandler;
import com.dimas.common.UserDetailsStore;
import com.dimas.exception.ApiAccessDeniedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.dimas.common.Constant.*;
import static java.util.Objects.isNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
public class UserDetailsFilter extends OncePerRequestFilter {

    private final AntPathMatcher antPathMatcher;
    private final UserDetailsStore userDetailsStore;
    private final ExceptionResponseHandler exceptionResponseHandler;
    private final UserDetailsFilterProperties userDetailsFilterProperties;

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        return userDetailsFilterProperties.getExcludedUrls().stream()
                .anyMatch(el -> antPathMatcher.match(el, request.getRequestURI()));
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws IOException {
        try {
            for (var headerName : GATEWAY_HEADER_NAMES) {
                String header = request.getHeader(headerName);
                if (isNull(header) && !userDetailsFilterProperties.getOptionalGatewayHeaderNames().contains(headerName)) {
                    throw new ApiAccessDeniedException("Mandatory header '" + headerName + "' is not provided");
                }
                setHeaderFromHeaderName(header, headerName);
            }
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            log.error("UserDetailsFilter exception", ex);
            final var status = HttpStatus.BAD_REQUEST.value();
            response.setStatus(status);
            response.setContentType(APPLICATION_JSON_VALUE);
            final var json = exceptionResponseHandler.exResponseToJson(request, HttpStatus.BAD_REQUEST, ex);
            response.getWriter().write(json);
        } finally {
            userDetailsStore.clear();
        }
    }

    private void setHeaderFromHeaderName(String header, String headerName) {
        if (isNull(header)) return;
        switch (headerName) {
            case X_API_USER_ID_HEADER_NAME:
                userDetailsStore.setUserId(Long.parseLong(header));
                break;
            case X_API_USER_NAME_HEADER_NAME:
                userDetailsStore.setUsername(header);
                break;
            default:
                break;
        }
    }
}
