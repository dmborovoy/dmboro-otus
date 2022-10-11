package com.dimas.order.saga;


import com.dimas.order.data.model.Saga;
import com.dimas.order.data.repository.SagaRepository;
import com.dimas.order.saga.command.SagaCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class SagaExecutor {

    private final SagaRepository sagaRepository;

    @Transactional
    public Saga init() {
        final var transactionId = UUID.randomUUID();
        log.info("[Saga] trxId={} is initiated", transactionId);
        final var saga = Saga.builder()
                .transactionId(transactionId)
                .stage(SagaStage.NEW)
                .status(SagaStatus.OK)
                .type(SagaType.FORWARD)
                .build();
        return sagaRepository.save(saga);
    }

    @Transactional
    public void orchestrate(Saga saga, List<SagaCommand> commands) {
        final var transactionId = saga.getTransactionId();
        log.info("[Saga] trxId={} is starter", transactionId);
        List<SagaCommand> performedCommands = new ArrayList<>();
        for (SagaCommand command : commands) {
            log.info("[Saga] trxId={}, started command={}", transactionId, command);
            final var result = command.execute();
            if (result) {
                log.info("[Saga] trxId={}, completed command={}", transactionId, command);
                final var commandSaga = Saga.builder()
                        .transactionId(transactionId)
                        .stage(command.getStage())
                        .status(SagaStatus.OK)
                        .type(SagaType.FORWARD)
                        .build();
                sagaRepository.save(commandSaga);
                performedCommands.add(command);
            } else {
                log.info("[Saga] trxId={}, FAILED command={}. Reverting", transactionId, command);
                Collections.reverse(performedCommands);
                revert(transactionId, performedCommands);
                final var commandSaga = Saga.builder()
                        .transactionId(transactionId)
                        .stage(SagaStage.REVERTED)
                        .status(SagaStatus.OK)
                        .type(SagaType.ROLLBACK)
                        .build();
                sagaRepository.save(commandSaga);
                break;
            }
        }
        log.info("[Saga] trxId={} is ended", transactionId);
    }

    @Transactional
    public void revert(UUID transactionId, List<SagaCommand> commands){
        for (SagaCommand command : commands) {
            log.info("[Saga] trxId={}, starting reverting command={}", transactionId, command);
            command.revert();
            log.info("[Saga] trxId={}, reverted successfully command={}", transactionId, command);
            final var commandSaga = Saga.builder()
                    .transactionId(transactionId)
                    .stage(command.getStage())
                    .status(SagaStatus.OK)
                    .type(SagaType.ROLLBACK)
                    .build();
            sagaRepository.save(commandSaga);
        }
    }
}
