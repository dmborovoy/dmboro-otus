package com.dimas.notification.controller;


import com.dimas.notification.api.ApiNotification;
import com.dimas.notification.api.ApiNotificationRequest;
import com.dimas.notification.service.EmailNotificationMapper;
import com.dimas.notification.service.NotificationService;
import com.dimas.notification.util.Constant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(Constant.ROOT_PATH)
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final EmailNotificationMapper mapper;
    private final static String PATH = "/notifications";

    @GetMapping(PATH)
    public List<ApiNotification> getAll() {
        return mapper.mapAsList(notificationService.getAll());
    }

    @GetMapping(PATH + "/{notificationId}")
    public ApiNotification getById(@PathVariable UUID notificationId) {
        return mapper.map(notificationService.findById(notificationId));
    }

    @PostMapping(PATH)
    public ApiNotification create(@RequestBody ApiNotificationRequest request) {
        return mapper.map(notificationService.create(request));
    }

}
