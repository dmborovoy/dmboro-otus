package com.dimas.order.api;


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
public class ApiOrder {
    private UUID id;
    private UUID userId;
    private UUID transactionId;
    private String status;
    private String description;
    private LocalDateTime createdOn;
    private LocalDateTime modifiedOn;
    private LocalDateTime completedOn;
    private Set<ApiItem> items;
}
