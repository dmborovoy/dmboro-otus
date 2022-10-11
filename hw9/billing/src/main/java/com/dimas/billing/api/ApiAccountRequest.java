package com.dimas.billing.api;

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
    private UUID id;//optional
    private Long userId;
    private String description;
    private BigDecimal initialBalance;
}
