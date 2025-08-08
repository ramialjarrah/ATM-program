package com.progressoft.training.atm.atm_machine.service.request;

import java.math.BigDecimal;

public record WithdrawRequest(String pin, BigDecimal amount) {
}
