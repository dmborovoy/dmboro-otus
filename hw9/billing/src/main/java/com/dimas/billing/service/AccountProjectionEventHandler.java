package com.dimas.billing.service;

import com.dimas.billing.data.model.Account;
import com.dimas.billing.data.model.AccountStatus;
import com.dimas.billing.data.repository.AccountRepository;
import com.dimas.cqrs.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class AccountProjectionEventHandler {

    private final AccountRepository accountRepository;

    @EventHandler
    public void accountCreateEventHandler(AccountCreatedEvent event) {
        log.info("Received event={}", event);
        final var account = Account.builder()
                .id(event.getAccountId())
                .userId(event.getUserId())
                .balance(event.getBalance())
                .status(AccountStatus.NORMAL)
                .createdOn(LocalDateTime.now())
                .build();
        final var saved = accountRepository.save(account);
        log.info("Account projection successfully saved={}", saved);
    }

    @EventHandler
    public void balanceUpdatedEventHandler(BalanceUpdatedEvent event) {
        log.info("Received event={}", event);
        final var account = accountRepository.getById(event.getAccountId());
        account.setBalance(event.getBalance());
        final var saved = accountRepository.save(account);
        log.info("Balance of account projection successfully updated={}", saved);
    }

    @QueryHandler
    public GetAccountQueryResult getAccountQueryHandler(GetAccountQuery query) {
        log.info("Received query={}", query);
        final var account = accountRepository.getById(query.getAccountId());
        return GetAccountQueryResult.builder()
                .accountId(account.getId())
                .userId(account.getUserId())
                .balance(account.getBalance())
                .build();
    }

    @QueryHandler
    public GetAllAccountsQueryResult getAllAccountsQueryHandler(GetAllAccountsQuery query) {//pretend that user has more then 1 acc
        log.info("Received query={}", query);
        final var accounts = accountRepository.findAll();
        return GetAllAccountsQueryResult.builder()
                .results(accounts.stream()
                        .map(e -> GetAccountQueryResult.builder()
                                .accountId(e.getId())
                                .userId(e.getUserId())
                                .balance(e.getBalance())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
