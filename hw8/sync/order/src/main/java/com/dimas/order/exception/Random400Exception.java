package com.dimas.order.exception;

public class Random400Exception extends RuntimeException {
    public Random400Exception(String message) {
        super(message);
    }

    public Random400Exception(String message, Throwable cause) {
        super(message, cause);
    }
}