package com.dimas.exception;

public class NotPermittedAccountException extends RuntimeException {
    public NotPermittedAccountException(String message) {
        super(message);
    }

    public NotPermittedAccountException(String message, Throwable cause) {
        super(message, cause);
    }
}