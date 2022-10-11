package com.dimas.order.controller;


import com.dimas.order.api.ApiSaga;
import com.dimas.order.data.repository.SagaRepository;
import com.dimas.order.service.SagaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static com.dimas.order.util.Constant.ROOT_PATH;

@Slf4j
@RestController
@RequestMapping(ROOT_PATH)
@RequiredArgsConstructor
public class SagaController {

    private final SagaMapper sagaMapper;
    private final SagaRepository sagaRepository;
    private final static String PATH = "/sagas";

    @GetMapping(PATH + "/{id}")
    public ApiSaga getById(@PathVariable UUID id) {
        return sagaMapper.map(sagaRepository.getById(id));
    }

    @GetMapping(PATH)
    public List<ApiSaga> getAll() {
        return sagaMapper.mapAsList(sagaRepository.findAll());
    }

}
