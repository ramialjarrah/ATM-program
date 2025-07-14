package com.progressoft.training.atm.services;

import com.progressoft.training.atm.category.CashCategory;

import java.math.BigDecimal;
import java.util.Map;

public interface AtmService {
    void deposit(BigDecimal amount, String pin);

    void withdraw(BigDecimal amount, String pin);

    BigDecimal checkBalance(String pin);

    void transfer(BigDecimal amount, String senderPin, String receiverPin);
}
