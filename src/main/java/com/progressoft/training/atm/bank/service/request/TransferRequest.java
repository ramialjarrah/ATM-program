package com.progressoft.training.atm.bank.service.request;

import java.math.BigDecimal;

public record TransferRequest(BigDecimal amount, String senderPin, String receiverPin) {
}
