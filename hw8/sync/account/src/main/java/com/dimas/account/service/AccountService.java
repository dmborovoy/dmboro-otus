package com.dimas.account.service;

import com.dimas.account.api.ApiAccount;
import com.dimas.account.api.ApiAccountRequest;
import com.dimas.account.api.ApiCreditRequest;
import com.dimas.account.api.ApiDebitRequest;
import com.dimas.account.data.model.Account;
import com.dimas.account.data.model.AccountStatus;
import com.dimas.account.data.repository.AccountRepository;
import com.dimas.account.exception.InsufficientFundsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;
    private final AccountMapper mapper;

    public ApiAccount findById(UUID id) {
        return mapper.map(repository.getById(id));
    }

    @Transactional
    public ApiAccount create(ApiAccountRequest request) {
        final var account = Account.builder()
                .userId(request.getUserId())
                .createdOn(LocalDateTime.now())
                .balance(request.getInitialBalance())//will be 0.0 if null due to default in db
                .description(request.getDescription())
                .status(AccountStatus.NORMAL)
                .build();
        return mapper.map(repository.save(account));
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public List<ApiAccount> getAll() {
        return mapper.mapAsList(repository.findAll());
    }

    @Transactional
    public ApiAccount debit(UUID accountId, ApiDebitRequest request) {
        log.info("Debit {} from accountId={}", request.getAmount(), accountId);
        final var account = repository.getById(accountId);
        final var targetBalance = account.getBalance().add(request.getAmount());
        account.setBalance(targetBalance);
        return mapper.map(repository.save(account));
    }

    @Transactional
    public ApiAccount credit(UUID accountId, ApiCreditRequest request) {
        log.info("Credit {} from accountId={}", request.getAmount(), accountId);
        final var account = repository.getById(accountId);
        final var targetBalance = account.getBalance().subtract(request.getAmount());
        if (targetBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientFundsException("Cannot credit amount=" + request.getAmount() + " from accountId=" + accountId);
        }
        account.setBalance(targetBalance);
        return mapper.map(repository.save(account));
    }
}
