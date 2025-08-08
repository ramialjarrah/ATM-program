package com.progressoft.training.atm.bank.service;

import com.progressoft.training.atm.bank.service.domain.UserDomain;
import com.progressoft.training.atm.bank.service.request.DepositRequest;
import com.progressoft.training.atm.bank.service.request.TransferRequest;
import com.progressoft.training.atm.bank.service.request.WithdrawRequest;

import java.math.BigDecimal;

public interface BankService {

    BigDecimal checkBalance(String pin);

    UserDomain getUserByPin(String pin);

    void deposit(DepositRequest depositRequest);

    void withdraw(WithdrawRequest withdrawRequest);

    void transfer(TransferRequest transferRequest);
}
