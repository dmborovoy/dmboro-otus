package com.dimas.notification.service;

import com.dimas.cqrs.NotificationPublishedEvent;
import com.dimas.cqrs.SendNotificationCommand;
import com.dimas.notification.api.ApiNotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationCommandHandler {

    private final NotificationService notificationService;
    private final EventGateway eventGateway;

    @CommandHandler
    public void handleNotificationCommand(SendNotificationCommand command) {
        log.info("Received command={}", command);
        final var notification = notificationService.create(ApiNotificationRequest.builder()//imitate that we send email notification
                .from(command.getFrom())
                .to(command.getTo())
                .body(command.getBody())
                .subject(command.getSubject())
                .status(command.getStatus())
                .build());
        eventGateway.publish(NotificationPublishedEvent.builder()
                .notificationId(notification.getId())
                .subject(notification.getSubject())
                .status(notification.getStatus())
                .publishedOn(LocalDateTime.now())
                .build());
        log.info("Successfully sent notification={}", notification);
    }

}
