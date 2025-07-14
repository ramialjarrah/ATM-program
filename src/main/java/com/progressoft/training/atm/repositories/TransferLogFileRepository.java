package com.progressoft.training.atm.repositories;

import java.math.BigDecimal;

public interface TransferLogFileRepository {

    void addTransfer(String senderPin, String receiverPin, BigDecimal amount);
}
