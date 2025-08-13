package com.progressoft.training.atm.atm_machine.service.request;

import com.progressoft.training.atm.atm_machine.service.domain.CashDomain;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Map;

@NotNull(message = "DepositRequest cannot be null")
public record DepositRequest(
        @NotEmpty
        @Size(min = 4, max = 4, message = "PIN must be 4 digits")
        String pin,
        @NotEmpty
        Map<CashDomain, Integer> cashDomains
) {
}
