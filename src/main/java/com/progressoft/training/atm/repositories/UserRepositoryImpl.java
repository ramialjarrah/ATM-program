package com.progressoft.training.atm.repositories;

import com.progressoft.training.atm.exceptions.ValidationException;
import com.progressoft.training.atm.services.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {


    private final ArrayList<User> users = new ArrayList<>();

    public UserRepositoryImpl() {

        users.add(new User("Rami", "1111", BigDecimal.valueOf(500)));
        users.add(new User("Abed", "2222", BigDecimal.valueOf(1000)));
        users.add(new User("Ibrahim", "12345", BigDecimal.valueOf(2000)));
        users.add(new User("Ahmed", "23456", BigDecimal.valueOf(3000)));

        users.add(new User("Saif", "3333", BigDecimal.valueOf(3000)));
        users.add(new User("Abeer", "4444", BigDecimal.valueOf(3000)));
        users.add(new User("mohammed", "5555", BigDecimal.valueOf(3000)));

    }

    public User getUserByPin(String pin) {
        if (pin == null) {
            throw new ValidationException("pin is null!");
        }
        for (User user : users) {
            if (user.getPin().equals(pin)) {
                return user;
            } else {
                throw new ValidationException("Invalid PIN: no such user found");
            }

        }
        return null;
    }

    public User getUserByUsername(String username) {
        if (username == null) {
            throw new ValidationException("username is null!");
        }
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }

        }
        return null;
    }

    public void updateUserBalance(String pin, BigDecimal newBalance) {
        if (pin == null || newBalance == null) {
            throw new ValidationException("balance and pin cannot be null!");
        }
        for (User user : users) {
            if (user.getPin().equals(pin)) {
                user.setBalance(newBalance);
            }
        }
    }

    public List<User> getAllUsers() {

        return users;
    }

    public BigDecimal getUserBalance(String pin) {
        if (pin == null) {
            throw new ValidationException("pin is null!");
        }
        for (User user : users) {
            if (user.getPin().equals(pin)) {
                return user.getBalance();
            }
        }
        return null;
    }
}
