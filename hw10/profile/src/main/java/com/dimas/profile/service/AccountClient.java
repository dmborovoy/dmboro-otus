package com.dimas.profile.service;

import com.dimas.cqrs.GetAccountQueryResult;
import com.dimas.cqrs.CreateAccountCommand;
import com.dimas.cqrs.DeleteAccountCommand;
import com.dimas.cqrs.GetAccountQuery;
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
    private final UserService userService;

    public UUID createAccount(Long userId, BigDecimal initialBalance) {
        final var accountId = UUID.randomUUID();
        final var res = commandGateway.sendAndWait(CreateAccountCommand.builder()
                .accountId(accountId)
                .userId(userId)
                .initialBalance(initialBalance)
                .build());
        return accountId;
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
        final var user = userService.findById(userId);
        final var query = GetAccountQuery.builder().accountId(user.getKeycloakId()).userId(userId).build();
        return queryGateway.query(query, ResponseTypes.instanceOf(GetAccountQueryResult.class));
    }
}
