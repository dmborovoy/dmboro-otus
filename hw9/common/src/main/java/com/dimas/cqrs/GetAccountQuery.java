package com.dimas.cqrs;

import lombok.*;

import java.util.UUID;


@With
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAccountQuery {
    private UUID accountId;
    private Long userId;
}
