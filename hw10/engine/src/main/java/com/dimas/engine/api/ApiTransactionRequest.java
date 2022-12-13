package com.dimas.engine.api;

import com.dimas.common.NotificationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ApiTransactionRequest {
    private NotificationStatus status;
    private String from;
    private String to;
    private String subject;
    private String body;
}
