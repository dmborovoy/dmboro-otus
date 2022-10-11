package com.dimas.order.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ApiOrderRequest {
    private UUID userId;
//    private UUID accountId;
    private UUID transactionId;
    private String description;
//    private BigDecimal amount;//in real world price will be taken from another service
    private Set<ApiItemRequest> items;
}
