package com.dimas.cqrs;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@With
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationPublishedEvent {
    private UUID notificationId;
    private NotificationStatus status;
    private String subject;
    private LocalDateTime publishedOn;
}
