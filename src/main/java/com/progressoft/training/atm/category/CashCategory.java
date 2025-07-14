package com.progressoft.training.atm.category;

import java.math.BigDecimal;

public enum CashCategory {

    FiveDinars(BigDecimal.valueOf(5)),
    TenDinars(BigDecimal.valueOf(10)),
    TwentyDinars(BigDecimal.valueOf(20)),
    FiftyDinars(BigDecimal.valueOf(50));

    private final BigDecimal categoryCount;

    public BigDecimal getCategoryCount() {
        return categoryCount;
    }

    CashCategory(BigDecimal categoryCount) {
        this.categoryCount = categoryCount;
    }

}
