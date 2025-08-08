package com.progressoft.training.atm.bank.mapper;

import com.progressoft.training.atm.bank.repository.entities.UserEntity;
import com.progressoft.training.atm.bank.service.domain.UserDomain;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserEntityDomainMapper {
    UserEntity userDomainToEntity(UserDomain userDomain);

    UserDomain userEntityToDomain(UserEntity userEntity);

    List<UserDomain> userEntityToDomainList(List<UserEntity> userEntities);

    List<UserEntity> userDomainToEntityList(List<UserDomain> userDomains);
}
