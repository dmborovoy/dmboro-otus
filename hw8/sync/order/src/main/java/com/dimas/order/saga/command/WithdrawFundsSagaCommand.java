package com.dimas.order.saga.command;

import com.dimas.order.client.AccountClient;
import com.dimas.order.saga.SagaStage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WithdrawFundsSagaCommand implements SagaCommand {

    private final AccountClient accountClient;
    private final AccountClient.ApiCreditRequest request;
    private final UUID accountIdRequest;

    @Override
    public boolean execute() {
        try {
            log.info("Request to accountId={}, AccountService={}", accountIdRequest, request);
            final var response = accountClient.credit(accountIdRequest, request);
            log.info("Response from AccountService={}", response);
        } catch (Exception ex) {
            log.info("Failed to call AccountService", ex);
            return false;
        }
        return true;
    }

    @Override
    public boolean revert() {
        try {
            log.info("Reverting Request to accountId={}, AccountService={}", accountIdRequest, request);
            final var debitRequest = AccountClient.ApiDebitRequest.builder().amount(request.getAmount()).userId(request.getUserId()).build();
            final var response = accountClient.debit(accountIdRequest, debitRequest);
            log.info("Reverting Response from AccountService={}", response);
        } catch (Exception ex) {
            log.info("Failed to revert call AccountService", ex);
            return false;
        }
        return true;
    }

    @Override
    public SagaStage getStage() {
        return SagaStage.ACCOUNT;
    }
}

