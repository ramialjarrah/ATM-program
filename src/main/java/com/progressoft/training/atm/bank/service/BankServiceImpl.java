package com.progressoft.training.atm.bank.service;

import com.progressoft.training.atm.bank.service.domain.UserDomain;
import com.progressoft.training.atm.bank.service.request.DepositRequest;
import com.progressoft.training.atm.bank.service.request.TransferRequest;
import com.progressoft.training.atm.bank.service.request.WithdrawRequest;
import com.progressoft.training.atm.exceptions.ValidationException;
import com.progressoft.training.atm.bank.repository.BankRepository;
import com.progressoft.training.atm.util.validators.BeanValidator;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;

public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;

    public BankServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public BigDecimal checkBalance(String pin) {
        if (pin == null) {
            throw new ValidationException("pin cannot be null!");
        }
        return bankRepository.getUserBalance(pin);
    }

    @Override
    public UserDomain getUserByPin(String pin) {
        return bankRepository.getUserByPin(pin);
    }

    @Override
    public void deposit(DepositRequest depositRequest) {
        BeanValidator.validate(depositRequest);
        UserDomain userDomain = getUserByPin(depositRequest.pin());
        userDomain.setBalance(userDomain.getBalance().add(depositRequest.amount()));
        bankRepository.updateUserBalance(userDomain);

    }

    @Override
    public void withdraw(WithdrawRequest withdrawRequest) {

        BeanValidator.validate(withdrawRequest);

        BigDecimal currentBalance = checkBalance(withdrawRequest.pin());

        if (withdrawRequest.amount().compareTo(currentBalance) > 0) {
            throw new ValidationException("Amount cannot be greater than current balance!");
        }

        if (currentBalance == null) {
            throw new ValidationException("Current balance is null!");
        }
        UserDomain userDomain = getUserByPin(withdrawRequest.pin());
        userDomain.setBalance(userDomain.getBalance().subtract(withdrawRequest.amount()));
        bankRepository.updateUserBalance(userDomain);

    }

    @Override
    @Transactional
    public void transfer(TransferRequest transferRequest) {

       BeanValidator.validate(transferRequest);

        if (transferRequest.senderPin().equals(transferRequest.receiverPin())) {
            throw new ValidationException("Cannot transfer to the same account!");
        }

        UserDomain senderUserDomain = getUserByPin(transferRequest.senderPin());

        if (transferRequest.amount().compareTo(senderUserDomain.getBalance()) > 0) {
            throw new ValidationException("amount cannot be greater than sender balance!");
        }

        UserDomain receiverUserDomain = getUserByPin(transferRequest.receiverPin());
        senderUserDomain.setBalance(senderUserDomain.getBalance().subtract(transferRequest.amount()));
        receiverUserDomain.setBalance(receiverUserDomain.getBalance().add(transferRequest.amount()));
            bankRepository.updateUserBalance(senderUserDomain);
            bankRepository.updateUserBalance(receiverUserDomain);

    }
}

