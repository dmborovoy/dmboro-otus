package com.dimas.engine.service.rule;

import com.dimas.engine.service.FraudRuleResult;
import com.dimas.engine.service.FraudRuleStatus;
import com.dimas.engine.service.FraudTransaction;
import lombok.Data;

import static java.util.Objects.isNull;

@Data
public class EmailRule implements IFraudRule {

    private Long ruleId = 1L;
    private final static String BAD_EMAIL = "bad@email.com";

    @Override
    public FraudRuleResult process(FraudTransaction fraudTransaction) {
        final var clientEmail = fraudTransaction.getPayload().getClientEmail();
        return FraudRuleResult.builder()
                .ruleId(ruleId)
                .status(checkIsBadEmail(clientEmail) ? FraudRuleStatus.FAILED : FraudRuleStatus.OK)
                .build();
    }

    private boolean checkIsBadEmail(String email) {
        if (isNull(email)) return true;
        return BAD_EMAIL.equals(email);
    }
}
