package com.progressoft.training.atm.atm_machine.service.request;

import java.math.BigDecimal;

public record TransferRequest(BigDecimal amount, String senderPin, String receiverPin) {
}
