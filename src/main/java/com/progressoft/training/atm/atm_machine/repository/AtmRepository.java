package com.progressoft.training.atm.atm_machine.repository;

import com.progressoft.training.atm.atm_machine.service.domain.CashDomain;

import java.math.BigDecimal;
import java.util.List;

public interface AtmRepository {

    void updateCategory(CashDomain cashDomain);

    List<CashDomain> listCashCategories();

    BigDecimal getActualAmount();

    CashDomain getCashById(String cashId);
}
