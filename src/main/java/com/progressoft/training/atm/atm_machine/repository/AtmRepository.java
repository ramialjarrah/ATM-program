package com.progressoft.training.atm.atm_machine.repository;

import com.progressoft.training.atm.atm_machine.service.domain.CashDomain;

import java.math.BigDecimal;
import java.util.List;

public interface AtmRepository {
    void saveCategory(CashDomain cashDomain);

    void updateCategory(CashDomain cashDomain);

    List<CashDomain> retrieveCashes();

    List<CashDomain> sortCashCategory();

    BigDecimal getActualAmount();
}
