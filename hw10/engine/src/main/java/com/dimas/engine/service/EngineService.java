package com.dimas.engine.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class EngineService {
    private final List<IPreProcessor> preProcessors;
    private final List<IPostProcessor> postProcessors;
    private final IFraudRulePoller rulePoller;
    public FraudTransactionResult process(FraudTransaction fraudTransaction) {
        log.info("[FRAUD {}] Started", fraudTransaction.getRequestId());
        log.info("[FRAUD {}] Running preprocessors", fraudTransaction.getRequestId());
        preProcessors.forEach(e -> e.process(fraudTransaction));
        log.info("[FRAUD {}] Running rules", fraudTransaction.getRequestId());
        final var result = doProcess(fraudTransaction);
        log.info("[FRAUD {}] Running postprocessors", fraudTransaction.getRequestId());
        postProcessors.forEach(e -> e.process(fraudTransaction));
        log.info("[FRAUD {}] Completed", fraudTransaction.getRequestId());
        return result;
    }

    private FraudTransactionResult doProcess(FraudTransaction fraudTransaction) {
        final var ruleSet = rulePoller.getRuleSet(fraudTransaction.getRuleSetId());
        final var ruleResults = ruleSet.getRules().stream()
                .map(e -> e.process(fraudTransaction))
                .collect(Collectors.toList());
        return FraudTransactionResult.builder()
                .results(ruleResults)
                .status(ruleResults.stream().anyMatch(e -> e.getStatus() == FraudRuleStatus.FAILED) ?
                        FraudTransactionStatus.REJECTED :
                        FraudTransactionStatus.OK)
                .build();
    }

}
