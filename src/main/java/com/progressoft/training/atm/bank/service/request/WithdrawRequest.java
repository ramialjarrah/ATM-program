package com.progressoft.training.atm.bank.service.request;

import java.math.BigDecimal;

public record WithdrawRequest(String pin, BigDecimal amount) {
}
