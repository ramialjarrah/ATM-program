package com.progressoft.training.atm.repositories;

import com.progressoft.training.atm.category.CashCategory;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AtmRepositoryImpl implements AtmRepository{
    Map<CashCategory, Integer> availableCash;

    public AtmRepositoryImpl() {
        this.availableCash = Map.of(
                CashCategory.FiveDinars, 2,
                CashCategory.TenDinars, 10,
                CashCategory.TwentyDinars, 3,
                CashCategory.FiftyDinars, 10
        );

    }
    public void updateCategory(CashCategory cashCategory, Integer numOfCategories) {

        availableCash.computeIfPresent(cashCategory, ( Key, oldValue) -> (oldValue +  numOfCategories));

    }

    public Map<CashCategory,Integer> retrieveCashMap(){

        return new HashMap<>(availableCash);
    }

    public List<Map.Entry<CashCategory, Integer>> sortCashCategory() {
        return availableCash.entrySet().stream()
                .sorted(Comparator.comparing(entry -> entry.getKey().getCategoryCount(), Comparator.reverseOrder()))
                .toList();
    }

    public BigDecimal getActualAmount(){
        BigDecimal actualAmount = BigDecimal.ZERO;

        for(Map.Entry<CashCategory,Integer> entry : availableCash.entrySet()){

            actualAmount = actualAmount.add(entry.getKey().getCategoryCount().multiply(BigDecimal.valueOf(entry.getValue())));
        }
        return actualAmount;
    }

}
