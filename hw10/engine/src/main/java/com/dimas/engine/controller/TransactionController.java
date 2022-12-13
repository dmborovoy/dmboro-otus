package com.dimas.engine.controller;


import com.dimas.engine.api.ApiTransaction;
import com.dimas.engine.api.ApiTransactionRequest;
import com.dimas.engine.delegate.TransactionDelegate;
import com.dimas.engine.util.Constant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.dimas.common.Constant.ROOT_PATH;

@Slf4j
@RestController
@RequestMapping(ROOT_PATH)
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionDelegate delegate;
    private final static String PATH = "/fraud";

    @PostMapping(PATH)
    public ApiTransaction fraud(@RequestBody ApiTransactionRequest request) {
        return delegate.fraud(request);
    }

}
