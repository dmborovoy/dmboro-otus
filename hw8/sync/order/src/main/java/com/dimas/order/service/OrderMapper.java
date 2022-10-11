package com.dimas.order.service;

import com.dimas.order.api.ApiOrder;
import com.dimas.order.data.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;
import java.util.UUID;

@Mapper(imports = {UUID.class}, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT, uses = {ItemMapper.class})
public interface OrderMapper {

    ApiOrder map(Order order);

    List<ApiOrder> mapAsList(List<Order> orders);

}
