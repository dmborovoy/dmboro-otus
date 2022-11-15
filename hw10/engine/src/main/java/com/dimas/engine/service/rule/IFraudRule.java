package com.dimas.engine.service.rule;

import com.dimas.engine.service.FraudRuleResult;
import com.dimas.engine.service.FraudTransaction;

public interface IFraudRule {
    FraudRuleResult process(FraudTransaction fraudTransaction);
}
