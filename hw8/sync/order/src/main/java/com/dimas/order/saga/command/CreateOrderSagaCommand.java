package com.dimas.order.saga.command;

import com.dimas.order.api.ApiOrderRequest;
import com.dimas.order.data.model.OrderStatus;
import com.dimas.order.saga.SagaStage;
import com.dimas.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CreateOrderSagaCommand implements SagaCommand {

    private final OrderService orderService;
    private final ApiOrderRequest request;

    @Override
    public boolean execute() {
        try {
            log.info("Request to OrderService={}", request);
            final var response = orderService.create(request);
            log.info("Response from OrderService={}", response);
        } catch (Exception ex) {
            log.info("Failed to call OrderService", ex);
            return false;
        }
        return true;
    }

    @Override
    public boolean revert() {
        log.info("Reverting OrderService={}. No action is needed since it is first in chain", request);
        orderService.updateStatus(request.getTransactionId(), OrderStatus.FAILED);
        //        we should mark order as failure, update order with bad status
        return true;
    }

    @Override
    public SagaStage getStage() {
        return SagaStage.ORDER;
    }
}
