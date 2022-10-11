package com.dimas.order.service;

import com.dimas.order.api.ApiSaga;
import com.dimas.order.data.model.Saga;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;
import java.util.UUID;

@Mapper(imports = {UUID.class}, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface SagaMapper {

    ApiSaga map(Saga saga);

    List<ApiSaga> mapAsList(List<Saga> sagas);

}
