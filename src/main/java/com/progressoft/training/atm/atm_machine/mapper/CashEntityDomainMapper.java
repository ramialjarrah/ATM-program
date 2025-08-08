package com.progressoft.training.atm.atm_machine.mapper;

import com.progressoft.training.atm.atm_machine.repository.entities.CashEntity;
import com.progressoft.training.atm.atm_machine.service.domain.CashDomain;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface CashEntityDomainMapper {


    CashEntity ToEntity(CashDomain cashDomain, Object ObjectFactory);

    CashDomain ToDomain(CashEntity cashEntity);

    List<CashDomain> ToDomainList(List<CashEntity> cashEntities);

    List<CashEntity> ToEntityList(List<CashDomain> cashDomains);
}
