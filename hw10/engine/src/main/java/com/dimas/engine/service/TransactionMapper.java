package com.dimas.engine.service;

import com.dimas.engine.api.ApiTransaction;
import com.dimas.engine.data.model.EmailNotification;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;
import java.util.UUID;

@Mapper(imports = {UUID.class}, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface TransactionMapper {

    ApiTransaction map(EmailNotification order);

    List<ApiTransaction> mapAsList(List<EmailNotification> orders);

}
