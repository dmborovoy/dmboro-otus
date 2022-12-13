package com.dimas.notification.api;


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
public class ApiNotification {
    private UUID id;
    private Long userId;
    private BigDecimal balance;
    private String status;
    private String description;
    private LocalDateTime createdOn;
    private LocalDateTime modifiedOn;
}
