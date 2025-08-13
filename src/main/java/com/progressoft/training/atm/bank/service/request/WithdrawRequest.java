package com.progressoft.training.atm.bank.service.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@NotNull(message = "WithdrawRequest cannot be null")
public record WithdrawRequest(

        @NotEmpty(message = "PIN cannot be null")
        String pin,

        @NotNull(message = "Amount cannot be null")
        @Positive(message = "Amount must be positive")
        BigDecimal amount) {
}


/*
car ->
    CarEntity (uuid, numberPlate, empUuid, )
    CarService
parking ->
    parkRepo ->
        ParkEntity (parkNo, carUuid, )
    parkService
 */