package com.dimas.profile.delegate;

import com.dimas.profile.api.ApiPermission;
import com.dimas.profile.data.model.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;
import java.util.UUID;

@Mapper(imports = {UUID.class}, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface PermissionMapper {

    ApiPermission map(Permission order);

    List<ApiPermission> mapAsList(List<Permission> orders);


}
