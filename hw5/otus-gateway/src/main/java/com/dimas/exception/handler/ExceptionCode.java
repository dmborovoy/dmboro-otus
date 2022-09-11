package com.dimas.exception.handler;

import com.dimas.exception.BaseApiException;
import com.dimas.exception.InvalidTenantSuppliedException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.Getter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.naming.NoPermissionException;
import javax.naming.OperationNotSupportedException;
import javax.validation.ConstraintViolationException;
import java.util.NoSuchElementException;

@Getter
public enum ExceptionCode {
    INVALID_TENANT_SUPPLIED(103, InvalidTenantSuppliedException.class),
    API_EXCEPTION(101, BaseApiException.class),

    OPERATION_NOT_SUPPORTED(11, OperationNotSupportedException.class),
    NO_PERMISSION(10, NoPermissionException.class),
    ACCESS_DENIED(9, AccessDeniedException.class),
    BIND_EXCEPTION(8, BindException.class),
    CONSTRAINT_VIOLATION(7, ConstraintViolationException.class),
    METHOD_ARGUMENT_NOT_VALID(6, MethodArgumentNotValidException.class),
    INVALID_FORMAT(5, InvalidFormatException.class),
    NO_SUCH_ELEMENT(4, NoSuchElementException.class),
    ILLEGAL_ARGUMENT(3, IllegalArgumentException.class),
    AUTHENTICATION_FAILED(2, OAuth2AuthenticationException.class),
    COMMON_ERROR(1, Exception.class);

    private final int code;
    private final Class<? extends Exception> type;

    ExceptionCode(int code, Class<? extends Exception> type) {
        this.code = code;
        this.type = type;
    }

    public static ExceptionCode mapException(Exception exception) {
        for (ExceptionCode exceptionCode : values()) {
            if (exceptionCode.getType().isInstance(exception)) {
                return exceptionCode;
            }
        }

        return COMMON_ERROR;
    }
}
