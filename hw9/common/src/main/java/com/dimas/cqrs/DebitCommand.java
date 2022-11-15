package com.dimas.cqrs;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.util.UUID;


@With
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DebitCommand {
    @TargetAggregateIdentifier
    private UUID accountId;
    private BigDecimal amount;
}
