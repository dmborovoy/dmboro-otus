package com.dimas.order.saga;

public enum SagaStage {
    NEW, ORDER, ACCOUNT, STOCK, DONE, REVERTED, FAILED
}
