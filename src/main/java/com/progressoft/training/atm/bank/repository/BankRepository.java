package com.progressoft.training.atm.bank.repository;

import com.progressoft.training.atm.bank.service.domain.UserDomain;

import java.math.BigDecimal;
import java.util.List;

public interface BankRepository {
    UserDomain getUserByPin(String pin);

    UserDomain getUserByUsername(String username);

    List<UserDomain> getAllUsers();

    BigDecimal getUserBalance(String pin);

    void updateUserBalance(UserDomain userDomain);
}
