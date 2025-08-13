package com.progressoft.training.atm.bank.service.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
@NotNull(message = "DepositRequest cannot be null")
public record DepositRequest(

        @NotNull(message = "Amount cannot be null")
        @Positive(message = "Amount must be positive")
        BigDecimal amount,

        @NotEmpty(message = "PIN cannot be null or empty")
        @Size(min = 4, max = 4, message = "PIN must be 4 digits")
        String pin
) { }