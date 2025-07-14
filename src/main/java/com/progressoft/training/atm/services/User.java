package com.progressoft.training.atm.services;

import com.progressoft.training.atm.exceptions.ValidationException;

import java.math.BigDecimal;

public class User {
    private String username;
    private String pin;
    BigDecimal balance;

    public User(){
        this.username = null;
        this.pin = null;
        this.balance = null;
    }

    public User(String username, String pin, BigDecimal balance) {
        this.username = username;
        this.pin = pin;
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if(username == null){
            throw new ValidationException("Username cannot be null!");
        } else {
            this.username = username;
        }
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        if(pin == null){
            throw new ValidationException("Pin cannot be null!");
        } else {
            this.pin = pin;
        }
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        if(balance == null){
            throw new ValidationException("Balance cannot be null!");
        }
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", pin='" + pin + '\'' +
                ", balance=" + balance +
                '}';
    }
}
