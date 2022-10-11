package com.dimas.user.service;

import com.dimas.user.api.ApiUser;
import com.dimas.user.api.ApiUserRequest;
import com.dimas.user.data.model.User;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;

@Mapper(imports = {UUID.class}, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface UserMapper {

    ApiUser map(User order);

    List<ApiUser> mapAsList(List<User> orders);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void patch(ApiUserRequest source, @MappingTarget User dest);

}
