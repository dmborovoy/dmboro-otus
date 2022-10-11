package com.dimas.billing.controller;


import com.dimas.billing.api.ApiAccountRequest;
import com.dimas.billing.api.ApiCreditRequest;
import com.dimas.billing.api.ApiDebitRequest;
import com.dimas.cqrs.GetAccountQueryResult;
import com.dimas.cqrs.GetAllAccountsQueryResult;
import com.dimas.billing.service.AccountClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.dimas.billing.util.Constant.ROOT_PATH;

@Slf4j
@RestController
@RequestMapping(ROOT_PATH)
@RequiredArgsConstructor
public class CqrsAccountController {

    private final AccountClient accountClient;
    private final static String PATH = "/cqrs/accounts";

    @GetMapping(PATH)
    public GetAllAccountsQueryResult getAll() {
        return accountClient.getAllAccounts();
    }

    @GetMapping(PATH + "/{accountId}")
    public GetAccountQueryResult getById(@PathVariable UUID accountId) {
        return accountClient.getAccountByAccountId(accountId);
    }

    @PostMapping(PATH)
    public UUID create(@RequestBody ApiAccountRequest request) {
        return accountClient.createAccount(request.getUserId(), request.getInitialBalance());
    }

    @PutMapping(PATH + "/{accountId}/debit")
    public void debit(@PathVariable UUID accountId, @RequestBody ApiDebitRequest request) {
        accountClient.debitAccount(accountId, request.getAmount());
    }

    @PutMapping(PATH + "/{accountId}/credit")
    public void credit(@PathVariable UUID accountId, @RequestBody ApiCreditRequest request) {
        accountClient.creditAccount(accountId, request.getAmount());
    }


}
