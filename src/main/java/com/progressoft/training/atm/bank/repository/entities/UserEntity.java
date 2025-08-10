package com.progressoft.training.atm.bank.repository.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class UserEntity {

    @Id
    @Column(name = "PIN", unique = true)

    @Size(min = 4, max = 4)
    private String pin;

    @Column(name = "USERNAME", unique = true)
    @NonNull
    @Size(min = 3, max = 20)
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
