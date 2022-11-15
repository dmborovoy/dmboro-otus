package com.dimas.engine.service.rule;

import com.dimas.engine.service.FraudRuleResult;
import com.dimas.engine.service.FraudRuleStatus;
import com.dimas.engine.service.FraudTransaction;
import lombok.Data;

import java.math.BigDecimal;

import static java.util.Objects.isNull;

@Data
public class InvalidAmountRule implements IFraudRule {

    private Long ruleId = 2L;

    @Override
    public FraudRuleResult process(FraudTransaction fraudTransaction) {
        final var amount = fraudTransaction.getPayload().getAmount();
        return FraudRuleResult.builder()
                .ruleId(ruleId)
                .status(checkIsBadAmount(amount) ? FraudRuleStatus.FAILED : FraudRuleStatus.OK)
                .build();
    }

    private boolean checkIsBadAmount(BigDecimal amount) {
        if (isNull(amount)) return true;
        return BigDecimal.ZERO.compareTo(amount) > 0;
    }
}
