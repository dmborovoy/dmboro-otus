package com.dimas.order.api;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ApiItem {
    private UUID id;
    private UUID goodId;
    private Integer count;
    private UUID orderId;
}
