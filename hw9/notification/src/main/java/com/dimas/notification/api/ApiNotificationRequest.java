package com.dimas.notification.api;

import com.dimas.cqrs.NotificationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ApiNotificationRequest {
    private NotificationStatus status;
    private String from;
    private String to;
    private String subject;
    private String body;
}
