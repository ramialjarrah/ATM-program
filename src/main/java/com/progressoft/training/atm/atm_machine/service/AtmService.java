package com.progressoft.training.atm.atm_machine.service;

import com.progressoft.training.atm.atm_machine.service.domain.CashDomain;
import com.progressoft.training.atm.atm_machine.service.request.DepositRequest;
import com.progressoft.training.atm.atm_machine.service.request.TransferRequest;
import com.progressoft.training.atm.atm_machine.service.request.WithdrawRequest;
import com.progressoft.training.atm.bank.service.domain.UserDomain;

import java.math.BigDecimal;
import java.util.List;

public interface AtmService {
    void deposit(DepositRequest depositRequest);

    void withdraw(WithdrawRequest withdrawRequest);

    BigDecimal checkBalance(String pin);

    void transfer(TransferRequest transferRequest);

    List<CashDomain> retrieveCashes();

    CashDomain getCashById(String cashId);

    UserDomain findUserByPin(String pin);
}
