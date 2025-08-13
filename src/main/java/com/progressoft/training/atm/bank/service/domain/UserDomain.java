package com.progressoft.training.atm.bank.service.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@NotNull(message = "UserDomain cannot be null")
public class UserDomain {

    @NotEmpty(message = "UUID cannot be null or empty")
    @Size(min = 4, max = 4, message = "PIN must be 4 digits")
    private
    String pin;

    @NotEmpty(message = "Username cannot be null or empty")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private
    String username;

    @NotNull(message = "Balance cannot be null")
    @Positive(message = "Balance must be positive")
    @Positive
    private
    BigDecimal balance;
}
