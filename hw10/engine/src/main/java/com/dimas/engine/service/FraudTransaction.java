package com.dimas.engine.service;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class FraudTransaction {

    private UUID requestId;
    private Long ruleSetId;
    private FraudTransactionPayload payload;
    private FraudTransactionContext context;

    @Data
    public static class FraudTransactionPayload{
        private String clientName;
        private String clientEmail;
        private BigDecimal amount;
        private String currency;
    }
}
