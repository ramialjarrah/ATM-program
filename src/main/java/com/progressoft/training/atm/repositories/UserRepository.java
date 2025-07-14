package com.progressoft.training.atm.repositories;

import com.progressoft.training.atm.services.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserRepository {

    User getUserByPin(String pin);
    User getUserByUsername(String username);
    List<User> getAllUsers();
    BigDecimal getUserBalance(String pin);
    void updateUserBalance(String pin, BigDecimal newBalance);
}
