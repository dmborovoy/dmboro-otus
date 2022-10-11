package com.dimas.stock.exception;

public class InsufficientGoodsException extends RuntimeException {
    public InsufficientGoodsException(String message) {
        super(message);
    }

    public InsufficientGoodsException(String message, Throwable cause) {
        super(message, cause);
    }
}