package com.dimas.cqrs;


import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@With
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAccountQueryResult {
    private UUID accountId;
    private Long userId;
    private BigDecimal balance;
}
