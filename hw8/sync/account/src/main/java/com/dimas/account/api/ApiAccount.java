package com.dimas.account.api;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ApiAccount {
    private UUID id;
    private UUID userId;
    private BigDecimal balance;
    private String status;
    private String description;
    private LocalDateTime createdOn;
    private LocalDateTime modifiedOn;
}
