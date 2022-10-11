package com.dimas.account.controller;


import com.dimas.account.api.ApiAccount;
import com.dimas.account.api.ApiAccountRequest;
import com.dimas.account.api.ApiCreditRequest;
import com.dimas.account.api.ApiDebitRequest;
import com.dimas.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.dimas.account.util.Constant.ROOT_PATH;

@Slf4j
@RestController
@RequestMapping(ROOT_PATH)
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final static String PATH = "/accounts";

    @GetMapping(PATH + "/{accountId}")
    public ApiAccount getById(@PathVariable UUID accountId) {
        return accountService.findById(accountId);
    }

    @GetMapping(PATH)
    public List<ApiAccount> getAll() {
        return accountService.getAll();
    }

    @PostMapping(PATH)
    public ApiAccount create(@RequestBody ApiAccountRequest request) {
        return accountService.create(request);
    }

    @DeleteMapping(PATH + "/{accountId}")
    public void delete(@PathVariable UUID accountId) {
        accountService.deleteById(accountId);
    }

    @PutMapping(PATH + "/{accountId}/debit")
    public ApiAccount debit(@PathVariable UUID accountId, @RequestBody ApiDebitRequest request) {
        return accountService.debit(accountId, request);
    }

    @PutMapping(PATH + "/{accountId}/credit")
    public ApiAccount credit(@PathVariable UUID accountId, @RequestBody ApiCreditRequest request) {
        return accountService.credit(accountId, request);
    }

}
