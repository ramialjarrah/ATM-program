package com.progressoft.training.atm.bank.service.request;

import java.math.BigDecimal;

public record DepositRequest(BigDecimal amount, String pin) { }