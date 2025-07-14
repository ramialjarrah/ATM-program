package com.progressoft.training.atm.services;

import com.progressoft.training.atm.exceptions.ValidationException;
import com.progressoft.training.atm.repositories.TransferLogFileRepository;
import com.progressoft.training.atm.repositories.UserRepository;

import java.math.BigDecimal;

public class BankServiceImpl implements BankService {

    private final UserRepository userRepository;
    private final TransferLogFileRepository  transferLogFileRepository;

    public BankServiceImpl(UserRepository userRepository, TransferLogFileRepository transferLogFileRepository) {
        this.userRepository = userRepository;
        this.transferLogFileRepository = transferLogFileRepository;
    }

    @Override
    public BigDecimal checkBalance(String pin) {
        if (pin == null) {
            throw new ValidationException("pin cannot be null!");

        }
        BigDecimal currentBalance = userRepository.getUserBalance(pin);

        return currentBalance;
    }


    @Override
    public void deposit(BigDecimal amount, String pin) {

        if (pin == null || amount == null) {

            throw new ValidationException("pin and balance cannot be null!");

        } else {

            BigDecimal currentBalance = checkBalance(pin);
            BigDecimal newBalance = currentBalance.add(amount);
            userRepository.updateUserBalance(pin, newBalance);
        }
    }

    @Override
    public void withdraw(BigDecimal amount, String pin) {
        if (pin == null || amount == null) {

            throw new ValidationException("pin and balance cannot be null!");
        } else {

            BigDecimal currentBalance = checkBalance(pin);
            if (currentBalance == null) {
                throw new ValidationException("Invalid PIN: no such user found");
            }

            BigDecimal newBalance = currentBalance.subtract(amount);
            userRepository.updateUserBalance(pin, newBalance);
        }
    }

    @Override
    public void transfer(BigDecimal amount, String senderPin, String receiverPin) {

        if (amount == null || senderPin == null || receiverPin == null) {
            throw new ValidationException("pin and balance cannot be null!");
        }

        if (senderPin.equals(receiverPin)) {
            throw new ValidationException("Cannot transfer to the same account!");
        }

        BigDecimal senderBalance = checkBalance(senderPin);

        if (amount.compareTo(senderBalance) > 0) {
            throw new ValidationException("amount cannot be greater than sender balance!");
        }

        BigDecimal receiverBalance = userRepository.getUserBalance(receiverPin);

        userRepository.updateUserBalance(senderPin, senderBalance.subtract(amount));
        userRepository.updateUserBalance(receiverPin, receiverBalance.add(amount));

        transferLogFileRepository.addTransfer(senderPin,receiverPin,amount);

    }
}

