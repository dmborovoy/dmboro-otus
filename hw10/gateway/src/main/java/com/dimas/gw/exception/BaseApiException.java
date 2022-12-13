package com.dimas.gw.exception;

public class BaseApiException extends RuntimeException {
    public BaseApiException(String message) {
        super(message);
    }
}
