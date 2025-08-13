package com.progressoft.training.atm.atm_machine.service.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@NotNull(message = "WithdrawRequest cannot be empty")
public record WithdrawRequest(
        @NotNull(message = "PIN cannot be null")
        @Size(min = 4, max = 4, message = "PIN must be 4 digits")
        String pin,

        @NotNull(message = "Amount cannot be null")
        @Positive(message = "Amount must be positive")
        BigDecimal amount
) {
}
