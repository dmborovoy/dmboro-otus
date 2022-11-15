package com.dimas.cqrs;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;


@With
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteAccountCommand {
    @TargetAggregateIdentifier
    private UUID accountId;
    private Long userId;
}
