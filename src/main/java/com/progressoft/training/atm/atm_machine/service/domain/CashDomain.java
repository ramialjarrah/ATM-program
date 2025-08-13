package com.progressoft.training.atm.atm_machine.service.domain;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

//TODO USE VALIDATION

@AllArgsConstructor
@Setter
@Getter
@NotNull(message = "CashDomain cannot be null")
public class CashDomain {

    @NotEmpty(message = "Cash ID cannot be null")
    private
    String cashID;

    @NotEmpty(message = "Cash name cannot be null")
    private
    String cashName;

    @NotNull(message = "Cash amount cannot be null")
    @Positive(message = "Cash amount must be positive")
    private
    BigDecimal cashAmount;

    @Positive(message = "Cash quantity must be positive")
    private
    int cashQuantity;
}
