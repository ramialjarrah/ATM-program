package com.progressoft.training.atm.atm_machine.mapper;

import com.progressoft.training.atm.atm_machine.repository.entities.CashEntity;
import com.progressoft.training.atm.atm_machine.service.domain.CashDomain;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface CashMapper {

    CashDomain ToDomain(CashEntity cashEntity);

    List<CashDomain> ToDomainList(List<CashEntity> cashEntities);

}
