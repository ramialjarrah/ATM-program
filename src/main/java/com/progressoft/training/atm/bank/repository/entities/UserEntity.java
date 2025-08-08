package com.progressoft.training.atm.bank.repository.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class UserEntity {

    @Id
    @Column(name = "PIN", unique = true)
    private String pin;

    @Column(name = "USERNAME", unique = true)
    private String username;
    @Column(name = "BALANCE")
    private BigDecimal balance;

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", pin='" + pin + '\'' +
                ", balance=" + balance +
                '}';
    }
}
