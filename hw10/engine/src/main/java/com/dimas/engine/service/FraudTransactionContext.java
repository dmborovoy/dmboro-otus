package com.dimas.engine.service;

import lombok.Data;

import java.util.Map;

@Data
public class FraudTransactionContext {
    Map<String, Object> context;
}
