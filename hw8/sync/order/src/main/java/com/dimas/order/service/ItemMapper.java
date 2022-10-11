package com.dimas.order.service;

import com.dimas.order.api.ApiItem;
import com.dimas.order.data.model.Item;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;
import java.util.UUID;

@Mapper(imports = {UUID.class}, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface ItemMapper {

    ApiItem map(Item source);

    List<ApiItem> mapAsList(List<Item> source);

}
