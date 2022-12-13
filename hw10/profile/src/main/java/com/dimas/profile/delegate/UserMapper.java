package com.dimas.profile.delegate;

import com.dimas.profile.api.ApiUser;
import com.dimas.profile.api.ApiUserRequest;
import com.dimas.profile.data.model.User;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;

@Mapper(imports = {UUID.class}, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT, uses = {PermissionMapper.class})
public interface UserMapper {

    ApiUser map(User order);

    List<ApiUser> mapAsList(List<User> orders);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void patch(ApiUserRequest source, @MappingTarget User dest);

}
