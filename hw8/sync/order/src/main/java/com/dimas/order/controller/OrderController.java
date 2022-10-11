package com.dimas.order.controller;


import com.dimas.order.api.ApiOrder;
import com.dimas.order.api.ApiOrderRequest;
import com.dimas.order.api.ApiSaga;
import com.dimas.order.api.ApiSagaRequest;
import com.dimas.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.dimas.order.util.Constant.ROOT_PATH;

@Slf4j
@RestController
@RequestMapping(ROOT_PATH)
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final static String PATH = "/orders";

    @GetMapping(PATH + "/{id}")
    public ApiOrder getById(@PathVariable UUID id) {
        return orderService.findById(id);
    }

    @GetMapping(PATH)
    public List<ApiOrder> getAll() {
        return orderService.getAll();
    }

    @PostMapping(PATH)
    public ApiOrder create(@RequestBody ApiOrderRequest request) {
        return orderService.create(request);
    }

    @PostMapping(PATH + "/saga")
    public ApiSaga saga(@RequestBody ApiSagaRequest request) {
        return orderService.saga(request);
    }

    @DeleteMapping(PATH + "/{id}")
    public void delete(@PathVariable UUID id) {
        orderService.deleteById(id);
    }

}
