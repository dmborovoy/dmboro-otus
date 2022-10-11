package com.dimas.account.service;

import com.dimas.account.api.ApiAccount;
import com.dimas.account.data.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;
import java.util.UUID;

@Mapper(imports = {UUID.class}, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface AccountMapper {

    ApiAccount map(Account order);

    List<ApiAccount> mapAsList(List<Account> orders);

}
