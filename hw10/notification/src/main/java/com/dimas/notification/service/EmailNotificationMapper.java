package com.dimas.notification.service;

import com.dimas.notification.api.ApiNotification;
import com.dimas.notification.data.model.EmailNotification;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;
import java.util.UUID;

@Mapper(imports = {UUID.class}, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface EmailNotificationMapper {

    ApiNotification map(EmailNotification order);

    List<ApiNotification> mapAsList(List<EmailNotification> orders);

}
