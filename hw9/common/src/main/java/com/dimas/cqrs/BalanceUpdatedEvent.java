package com.dimas.cqrs;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;


@With
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalanceUpdatedEvent {
    private UUID accountId;
    private BigDecimal balance;
}
