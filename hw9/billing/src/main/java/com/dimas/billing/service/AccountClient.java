package com.dimas.billing.service;

import com.dimas.cqrs.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountClient {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public UUID createAccount(Long userId, BigDecimal initialBalance) {
        final var accountId = UUID.randomUUID();
        final var res = commandGateway.sendAndWait(CreateAccountCommand.builder()
                .accountId(accountId)
                .userId(userId)
                .initialBalance(initialBalance)
                .build());
        return accountId;
    }

    public void debitAccount(UUID accountId, BigDecimal amount) {
        final var res = commandGateway.sendAndWait(DebitCommand.builder()
                .accountId(accountId)
                .amount(amount)
                .build());
    }

    public void creditAccount(UUID accountId, BigDecimal amount) {
        final var res = commandGateway.sendAndWait(CreditCommand.builder()
                .accountId(accountId)
                .amount(amount)
                .build());
    }

    public void deleteAccount(Long userId) {
        final var accountId = UUID.randomUUID();
        final var res = commandGateway.sendAndWait(DeleteAccountCommand.builder()
                .accountId(accountId)
                .userId(userId)
                .build());
    }

    @SneakyThrows
    public GetAccountQueryResult getAccountByAccountId(UUID accountId) {
        return getAccountByAccountIdAsync(accountId).get();
    }

    public CompletableFuture<GetAccountQueryResult> getAccountByAccountIdAsync(UUID accountId) {
        final var query = GetAccountQuery.builder().accountId(accountId).build();
        return queryGateway.query(query, ResponseTypes.instanceOf(GetAccountQueryResult.class));
    }

    @SneakyThrows
    public GetAccountQueryResult getAccountByUserId(Long userId) {
        return getAccountByUserIdAsync(userId).get();
    }

    public CompletableFuture<GetAccountQueryResult> getAccountByUserIdAsync(Long userId) {
        final var query = GetAccountQuery.builder().userId(userId).build();
        return queryGateway.query(query, ResponseTypes.instanceOf(GetAccountQueryResult.class));
    }

    @SneakyThrows
    public GetAllAccountsQueryResult getAllAccounts() {
        final var query = GetAllAccountsQuery.builder().build();
        return queryGateway.query(query, ResponseTypes.instanceOf(GetAllAccountsQueryResult.class)).get();
    }
}
