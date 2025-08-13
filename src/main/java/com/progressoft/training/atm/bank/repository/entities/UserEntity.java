package com.progressoft.training.atm.bank.repository.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "USER")
public class UserEntity {

    @Id
    @NotEmpty(message = "UUID cannot be null or empty")
    @Column(name = "UUID", unique = true)
    String uuid;

    @NotEmpty(message = "PIN cannot be null or empty")
    @Column(name = "PIN", unique = true)
    @Size(min = 4, max = 4, message = "PIN must be 4 digits")
    private String pin;

    @Column(name = "USERNAME", unique = true)
    @NotEmpty(message = "Username cannot be null or empty")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @Column(name = "BALANCE")
    @Positive(message = "Balance must be positive")
    private BigDecimal balance;
}
