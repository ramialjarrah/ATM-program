package com.progressoft.training.atm.repositories;

import com.progressoft.training.atm.category.CashCategory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface AtmRepository {

    void updateCategory(CashCategory cashCategory , Integer numOfCategories);
    Map<CashCategory,Integer> retrieveCashMap();
    List<Map.Entry<CashCategory, Integer>> sortCashCategory();
    BigDecimal getActualAmount();
}
