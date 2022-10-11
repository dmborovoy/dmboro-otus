package com.dimas.stock.service;

import com.dimas.stock.api.ApiGood;
import com.dimas.stock.api.ApiGoodRequest;
import com.dimas.stock.api.ApiReserveRequest;
import com.dimas.stock.data.model.Good;
import com.dimas.stock.data.repository.GoodRepository;
import com.dimas.stock.exception.InsufficientGoodsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class GoodService {

    private final GoodRepository repository;
    private final GoodMapper mapper;

    public ApiGood findById(UUID id) {
        return mapper.map(repository.getById(id));
    }

    @Transactional
    public ApiGood create(ApiGoodRequest request) {
        final var good = Good.builder()
                .department(request.getDepartment())
                .name(request.getName())
                .count(request.getCount())
                .createdOn(LocalDateTime.now())
                .description(request.getDescription())
                .build();
        return mapper.map(repository.save(good));
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public List<ApiGood> getAll() {
        return mapper.mapAsList(repository.findAll());
    }

    @Transactional
    public ApiGood reserve(UUID goodId, ApiReserveRequest request) {
        log.info("Reserving {} items of goodId={}", request.getCount(), goodId);
        final var good = repository.getById(goodId);
        final var targetCount = good.getCount() - request.getCount();
        if (targetCount < 0) {
            throw new InsufficientGoodsException("Cannot reserve " + request.getCount() + " items" + " of goodId=" + goodId);
        }
        good.setCount(targetCount);
        return mapper.map(repository.save(good));
    }

    @Transactional
    public ApiGood unreserve(UUID goodId, ApiReserveRequest request) {
        log.info("Unreserving {} items of goodId={}", request.getCount(), goodId);
        final var good = repository.getById(goodId);
        final var targetCount = good.getCount() + request.getCount();
        good.setCount(targetCount);
        return mapper.map(repository.save(good));
    }
}
