package com.dimas.exception;

public class BaseApiException extends RuntimeException {
    public BaseApiException(String message) {
        super(message);
    }
}
