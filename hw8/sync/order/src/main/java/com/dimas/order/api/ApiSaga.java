package com.dimas.order.api;


import com.dimas.order.saga.SagaStage;
import com.dimas.order.saga.SagaStatus;
import com.dimas.order.saga.SagaType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ApiSaga {
    private UUID id;
    private UUID transactionId;
    private SagaStage stage;
    private SagaType type;
    private SagaStatus status;
    private String error;
}
