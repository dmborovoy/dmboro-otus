package com.dimas.gw.exception.handler;

import com.dimas.gw.utils.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.dimas.gw.utils.Utils.mapToString;
import static java.util.Objects.isNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExceptionResponseHandler {

    private static final boolean INCLUDE_STACK_TRACE = false;
    private final ObjectMapper objectMapper;

    public Mono<Void> exceptionToResponse(ServerWebExchange exchange, HttpStatus status, Throwable e) {
        final var response = exchange.getResponse();
        log.warn(e.getMessage(), e);
        response.setStatusCode(status);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE);
        final var json = exResponseToJson(exchange.getRequest(), status, (Exception) e);
        DataBuffer buffer = response.bufferFactory().wrap(json.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Flux.just(buffer));
    }

    @SneakyThrows
    public String exResponseToJson(ServerHttpRequest request, HttpStatus status, Exception ex) {
        return objectMapper.writeValueAsString(Collections.unmodifiableMap(getExResponseMap(request.getURI().getPath(), status, ex)));
    }

    public Map<String, String> getExResponseMap(String uri, HttpStatus status, Exception ex) {
        final var code = ExceptionCode.mapException(ex);
        final var map = new HashMap<>(Map.of(
                Constants.API_ERROR_LBL_TIMESTAMP, new SimpleDateFormat(Constants.TIMESTAMP_DATE_FORMAT).format(new Date()),
                Constants.API_ERROR_LBL_PATH, uri,
                Constants.API_ERROR_LBL_STATUS, String.valueOf(status.value()),
                Constants.API_ERROR_LBL_ERRORS, String.format("%s[%d]", code.name(), code.getCode()),
                Constants.API_ERROR_LBL_MESSAGE, getExceptionMessage(ex, status)
        ));

        if (INCLUDE_STACK_TRACE) {
            final var stringWriter = new StringWriter();
            final var printWriter = new PrintWriter(stringWriter);
            ex.printStackTrace(printWriter);
            printWriter.flush();
            map.put("trace", printWriter.toString());
        }

        return map;
    }

    private String getExceptionMessage(Exception ex, HttpStatus status) {
        if (ex instanceof BindException) {
            return extractBindingException(((BindException) ex).getBindingResult());
        }
        if (ex instanceof ConstraintViolationException) {
            return extractConstraintViolationException((ConstraintViolationException) ex);
        }
        return !isNull(ex.getMessage()) && !ex.getMessage().isEmpty() ? ex.getMessage() : status.getReasonPhrase();
    }

    private String extractConstraintViolationException(ConstraintViolationException e) {
        return e.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(";"));
    }

    private String extractBindingException(BindingResult bindingResult) {
        var errors = new HashMap<String, String>();
        bindingResult.getAllErrors().forEach(error -> {
            final var fieldName = ((FieldError) error).getField();
            final var errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return mapToString(errors);
    }
}
