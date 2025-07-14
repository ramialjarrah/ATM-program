package com.progressoft.training.atm.services;

import java.math.BigDecimal;

public interface BankService {

    BigDecimal checkBalance(String pin);

    void deposit(BigDecimal amount, String pin);
    void withdraw(BigDecimal amount, String pin);
    void transfer(BigDecimal amount, String senderPin, String receiverPin);
}
