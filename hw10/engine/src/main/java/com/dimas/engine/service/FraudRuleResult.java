package com.dimas.engine.service;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class FraudRuleResult {
    private Long ruleId;
    private FraudRuleStatus status;
    private Map<String, Object> context;
}
