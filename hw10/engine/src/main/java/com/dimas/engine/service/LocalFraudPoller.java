package com.dimas.engine.service;

import com.dimas.engine.service.rule.EmailRule;
import com.dimas.engine.service.rule.InvalidAmountRule;

import java.util.List;
import java.util.Map;

public class LocalFraudPoller implements IFraudRulePoller {

    private final Map<Long, RuleSet> ruleSetStorage = Map.of(3L, RuleSet.builder().ruleSetId(3L).rules(List.of(new EmailRule(), new InvalidAmountRule())).build());

    //    по идее рулы должны содержать внутри скрипты интерпретируемые, но ну его нафиг. Для тестовог7о проекта пожет захардкодить это дело
    @Override
    public RuleSet getRuleSet(Long ruleSetId) {
        return ruleSetStorage.get(ruleSetId);
    }
}
