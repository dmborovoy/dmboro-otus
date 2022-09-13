package com.dimas.exception;

public class IdempotenceException extends RuntimeException {
    public IdempotenceException(String message) {
        super(message);
    }

    public IdempotenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
