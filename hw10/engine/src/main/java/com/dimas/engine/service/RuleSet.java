package com.dimas.engine.service;

import com.dimas.engine.service.rule.IFraudRule;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RuleSet {

    Long ruleSetId;
    List<IFraudRule> rules;
}
