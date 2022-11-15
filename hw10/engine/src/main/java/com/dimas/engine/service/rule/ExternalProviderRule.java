package com.dimas.engine.service.rule;

import com.dimas.engine.service.FraudRuleResult;
import com.dimas.engine.service.FraudRuleStatus;
import com.dimas.engine.service.FraudTransaction;
import lombok.Data;

import static java.util.Objects.isNull;

@Data
public class ExternalProviderRule implements IFraudRule {

    private Long ruleId = 3L;


    @Override
    public FraudRuleResult process(FraudTransaction fraudTransaction) {
        //calling external provider
        return FraudRuleResult.builder()
                .ruleId(ruleId)
                .status(FraudRuleStatus.OK)
                .build();
    }

}
