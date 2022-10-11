package com.dimas.order.saga.command;

import com.dimas.order.saga.SagaStage;

public interface SagaCommand {

    boolean execute();

    boolean revert();

    SagaStage getStage();
}
