package com.progressoft.training.atm.bank.repository;

import com.progressoft.training.atm.bank.service.domain.UserDomain;

import java.math.BigDecimal;

public interface BankRepository {
    UserDomain getUserByPin(String pin);

    BigDecimal getUserBalance(String pin);

    void updateUserBalance(UserDomain userDomain);
}
