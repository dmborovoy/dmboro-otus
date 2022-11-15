package com.dimas.engine.service;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FraudTransactionResult {
    private FraudTransactionStatus status;
    private List<FraudRuleResult> results;
}
