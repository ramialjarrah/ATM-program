package com.progressoft.training.atm.bank.mapper;

import com.progressoft.training.atm.bank.repository.entities.UserEntity;
import com.progressoft.training.atm.bank.service.domain.UserDomain;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserDomain userEntityToDomain(UserEntity userEntity);
}
