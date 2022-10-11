package com.dimas.stock.service;

import com.dimas.stock.api.ApiGood;
import com.dimas.stock.data.model.Good;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;
import java.util.UUID;

@Mapper(imports = {UUID.class}, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface GoodMapper {

    ApiGood map(Good order);

    List<ApiGood> mapAsList(List<Good> orders);

}
