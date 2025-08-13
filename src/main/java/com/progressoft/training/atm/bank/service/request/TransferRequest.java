package com.progressoft.training.atm.bank.service.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@NotNull(message = "TransferRequest cannot be null")
public record TransferRequest(

        @NotNull(message = "Amount cannot be null or empty")
        @Positive(message = "Amount must be positive")
        BigDecimal amount,

        @NotEmpty(message = "Sender PIN cannot be null or empty")
        @Size(min = 4, max = 4, message = "PIN must be 4 digits")
        String senderPin,

        @NotEmpty(message = "Receiver PIN cannot be null or empty")
        @Size(min = 4, max = 4, message = "PIN must be 4 digits")
        String receiverPin
) {
}
