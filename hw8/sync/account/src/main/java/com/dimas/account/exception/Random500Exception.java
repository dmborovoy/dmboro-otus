package com.dimas.account.exception;

public class Random500Exception extends RuntimeException {
    public Random500Exception(String message) {
        super(message);
    }

    public Random500Exception(String message, Throwable cause) {
        super(message, cause);
    }
}