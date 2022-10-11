package com.dimas.order.saga.command;

import com.dimas.order.client.AccountClient;
import com.dimas.order.client.StockClient;
import com.dimas.order.saga.SagaStage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReserveItemsSagaCommand implements SagaCommand {

    private final StockClient stockClient;
    private final StockClient.ApiReserveRequest request;
    private final UUID goodIdRequest;

    @Override
    public boolean execute() {
        try {
            log.info("Request to goodId={}, StockClient={}", goodIdRequest, request);
            final var response = stockClient.reserve(goodIdRequest, request);
            log.info("Response from StockClient={}", response);
        } catch (Exception ex) {
            log.info("Failed to call StockClient", ex);
            return false;
        }
        return true;
    }

    @Override
    public boolean revert() {
        try {
            log.info("Reverting Request to goodId={}, StockClient={}", goodIdRequest, request);
            final var response = stockClient.unreserve(goodIdRequest, request);
            log.info("Reverting Response from StockClient={}", response);
        } catch (Exception ex) {
            log.info("Failed to call StockClient", ex);
            return false;
        }
        return true;
    }
    @Override
    public SagaStage getStage() {
        return SagaStage.STOCK;
    }
}
