package com.dimas.account.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ApiAccountRequest {
    private UUID userId;
    private String description;
    private BigDecimal initialBalance;
}
