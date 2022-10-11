package com.dimas.stock.controller;


import com.dimas.stock.api.ApiGood;
import com.dimas.stock.api.ApiGoodRequest;
import com.dimas.stock.api.ApiReserveRequest;
import com.dimas.stock.service.GoodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.dimas.stock.util.Constant.ROOT_PATH;

@Slf4j
@RestController
@RequestMapping(ROOT_PATH)
@RequiredArgsConstructor
public class GoodController {

    private final GoodService goodService;
    private final static String PATH = "/goods";

    @GetMapping(PATH + "/{goodId}")
    public ApiGood getById(@PathVariable UUID goodId) {
        return goodService.findById(goodId);
    }

    @GetMapping(PATH)
    public List<ApiGood> getAll() {
        return goodService.getAll();
    }

    @PostMapping(PATH)
    public ApiGood create(@RequestBody ApiGoodRequest request) {
        return goodService.create(request);
    }

    @DeleteMapping(PATH + "/{goodId}")
    public void delete(@PathVariable UUID goodId) {
        goodService.deleteById(goodId);
    }

    @PutMapping(PATH + "/{goodId}/reserve")//should accept array?
    public ApiGood reserve(@PathVariable UUID goodId, @RequestBody ApiReserveRequest request) {
        return goodService.reserve(goodId, request);
    }

    @PutMapping(PATH + "/{goodId}/unreserve")
    public ApiGood unreserve(@PathVariable UUID goodId, @RequestBody ApiReserveRequest request) {
        return goodService.unreserve(goodId, request);
    }

}
