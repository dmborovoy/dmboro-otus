package com.dimas.order.exception;

public class Random500Exception extends RuntimeException {
    public Random500Exception(String message) {
        super(message);
    }

    public Random500Exception(String message, Throwable cause) {
        super(message, cause);
    }
}