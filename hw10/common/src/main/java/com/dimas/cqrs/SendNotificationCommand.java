package com.dimas.cqrs;

import com.dimas.common.NotificationStatus;
import lombok.*;

@With
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendNotificationCommand {
    private String from;
    private String to;
    private String subject;
    private String body;
    private NotificationStatus status;
}
