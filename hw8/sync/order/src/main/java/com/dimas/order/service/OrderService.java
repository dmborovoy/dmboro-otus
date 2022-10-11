package com.dimas.order.service;

import com.dimas.order.api.ApiOrder;
import com.dimas.order.api.ApiOrderRequest;
import com.dimas.order.api.ApiSaga;
import com.dimas.order.api.ApiSagaRequest;
import com.dimas.order.client.AccountClient;
import com.dimas.order.client.StockClient;
import com.dimas.order.data.model.Item;
import com.dimas.order.data.model.Order;
import com.dimas.order.data.model.OrderStatus;
import com.dimas.order.data.repository.ItemRepository;
import com.dimas.order.data.repository.OrderRepository;
import com.dimas.order.exception.Random400Exception;
import com.dimas.order.exception.Random500Exception;
import com.dimas.order.saga.SagaExecutor;
import com.dimas.order.saga.command.CreateOrderSagaCommand;
import com.dimas.order.saga.command.ReserveItemsSagaCommand;
import com.dimas.order.saga.command.SagaCommand;
import com.dimas.order.saga.command.WithdrawFundsSagaCommand;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final ItemRepository itemRepository;
    private final OrderMapper mapper;
    private final SagaMapper sagaMapper;
    private final SagaExecutor sagaExecutor;

//    private final OrderService orderService;
    private final AccountClient accountClient;
    private final StockClient stockClient;


    public ApiOrder findById(UUID id) {
        return mapper.map(repository.getById(id));
    }

    @Transactional
    public ApiOrder updateStatus(UUID transactionId, OrderStatus status) {
        final var order = repository.findByTransactionId(transactionId);
        order.setStatus(status);
        return mapper.map(repository.save(order));
    }

    @Transactional
    public ApiOrder create(ApiOrderRequest request) {
        final var order = Order.builder()
                .transactionId(request.getTransactionId())
                .userId(request.getUserId())
                .status(OrderStatus.NEW)
                .description(request.getDescription())
                .createdOn(LocalDateTime.now())
                .build();
        final var savedOrder = repository.save(order);
        log.info("orderId={}", order.getId());
        final var items = request.getItems().stream()
                        .map(e -> Item.builder()
                                .orderId(order.getId())
                                .count(e.getCount())
                                .goodId(e.getGoodId())
                                .build())
                        .collect(Collectors.toList());
        final var savedItems = itemRepository.saveAll(items);
        savedOrder.setItems(savedItems);
        return mapper.map(savedOrder);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public List<ApiOrder> getAll() {
        return mapper.mapAsList(repository.findAll());
    }

    public ApiSaga saga(ApiSagaRequest request) {
        final var saga = sagaExecutor.init();
        final var createOrderCommand = new CreateOrderSagaCommand(this, ApiOrderRequest.builder().userId(request.getUserId()).transactionId(request.getTransactionId()).description(request.getDescription()).items(request.getItems()).build());
        final var withdrawFundsSagaCommand = new WithdrawFundsSagaCommand(accountClient, AccountClient.ApiCreditRequest.builder().amount(request.getAmount()).build(), request.getAccountId());
        final var firstItem = request.getItems().stream().findFirst().get();
        final var reserveItemsSagaCommand = new ReserveItemsSagaCommand(stockClient, StockClient.ApiReserveRequest.builder().count(firstItem.getCount()).build(), firstItem.getGoodId());

        List<SagaCommand> commands = Arrays.asList(createOrderCommand, withdrawFundsSagaCommand, reserveItemsSagaCommand);
        sagaExecutor.orchestrate(saga, commands);//TODO run async
        return sagaMapper.map(saga);
    }
}
