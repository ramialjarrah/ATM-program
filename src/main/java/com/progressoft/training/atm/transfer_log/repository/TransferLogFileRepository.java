package com.progressoft.training.atm.transfer_log.repository;

import com.progressoft.training.atm.bank.service.request.TransferRequest;

import java.math.BigDecimal;

public interface TransferLogFileRepository {

    void addTransfer(TransferRequest transferRequest);
}
