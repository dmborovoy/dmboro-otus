package com.dimas.profile.service;

import com.dimas.common.NotificationStatus;
import com.dimas.cqrs.SendNotificationCommand;
import com.dimas.profile.data.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationClient {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public boolean sendNotification(User user, String body, NotificationStatus status) {
        final var res = commandGateway.sendAndWait(SendNotificationCommand.builder()
                .to(user.getFirstName() + user.getLastName() + "@email.com")
                .from("noreply@email.com")
                .body(body)
                .subject(status.name())
                .status(status)
                .build());
        return true;
    }

}
