package com.dimas.service;

import com.dimas.api.ApiUser;
import com.dimas.data.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;
import java.util.UUID;

@Mapper(imports = {UUID.class}, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface UserMapper {

    ApiUser map(User user);

    List<ApiUser> map(List<User> users);

}
