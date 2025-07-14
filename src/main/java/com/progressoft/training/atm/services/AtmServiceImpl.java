package com.progressoft.training.atm.services;

import com.progressoft.training.atm.category.CashCategory;
import com.progressoft.training.atm.exceptions.ValidationException;
import com.progressoft.training.atm.repositories.AtmRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AtmServiceImpl implements AtmService {
    private final BankService bankService;
    private final AtmRepository atmRepository;

    public AtmServiceImpl(BankService bankService, AtmRepository atmRepository) {
        this.bankService = bankService;
        this.atmRepository = atmRepository;
    }

    @Override
    public void deposit(BigDecimal amount, String pin) {
        if (amount == null || pin == null) {
            throw new ValidationException("amount and pin cannot be null!");
        }

        if (!amount.remainder(BigDecimal.valueOf(5)).equals(BigDecimal.ZERO)) {
            throw new ValidationException("Amount must be a multiple of 5!");
        }

        Map<CashCategory, Integer> addedCategories = calculateAddedCategories(amount);

        for (Map.Entry<CashCategory, Integer> entry : addedCategories.entrySet()) {
            atmRepository.updateCategory(entry.getKey(), entry.getValue());
        }

        bankService.deposit(amount, pin);
    }

    private Map<CashCategory, Integer> calculateAddedCategories(BigDecimal amount) {
        Map<CashCategory, Integer> addedCategories = new HashMap<>();

        BigDecimal totalAmount = amount;
        List<Map.Entry<CashCategory, Integer>> sortedCategories = atmRepository.sortCashCategory();

        for (Map.Entry<CashCategory, Integer> entry : sortedCategories) {
            CashCategory category = entry.getKey();
            BigDecimal denomination = category.getCategoryCount();

            int neededCategories = totalAmount.divide(denomination, 0, RoundingMode.DOWN).intValue();

            if (neededCategories > 0) {
                addedCategories.put(category, neededCategories);
            }
            totalAmount = totalAmount.subtract(denomination.multiply(BigDecimal.valueOf(neededCategories)));

            if (totalAmount.compareTo(BigDecimal.ZERO) == 0) {
                break;
            }
        }

        if (totalAmount.compareTo(BigDecimal.ZERO) > 0) {
            throw new ValidationException("Cannot dispense the exact amount with available denomination");
        }
        return addedCategories;
    }

    @Override
    public void withdraw(BigDecimal amount, String pin) {

        if (amount == null || pin == null) {
            throw new ValidationException("amount and pin cannot be null!");
        }

        if (amount.compareTo(atmRepository.getActualAmount()) > 0) {
            throw new ValidationException("Amount in the ATM is less than the amount you want to withdraw!");
        }

        Map<CashCategory, Integer> dispenseCategories = calculateDispenseCategories(amount);

        for (Map.Entry<CashCategory, Integer> entry : dispenseCategories.entrySet()) {
            atmRepository.updateCategory(entry.getKey(), -entry.getValue());
        }

        bankService.withdraw(amount, pin);
    }

    private Map<CashCategory, Integer> calculateDispenseCategories(BigDecimal amount) {
        Map<CashCategory, Integer> dispenseCategories = new HashMap<>();

        BigDecimal remainingAmount = amount;

        List<Map.Entry<CashCategory, Integer>> sortedCategories = atmRepository.sortCashCategory();

        for (Map.Entry<CashCategory, Integer> entry : sortedCategories) {
            CashCategory category = entry.getKey();
            int availableCategories = entry.getValue();
            BigDecimal denomination = category.getCategoryCount();

            int neededCategories = remainingAmount.divide(denomination, 0, RoundingMode.DOWN).intValue();
            int categoriesToDispense = Math.min(neededCategories, availableCategories);

            if (categoriesToDispense > 0) {
                dispenseCategories.put(category, categoriesToDispense);
                remainingAmount = remainingAmount.subtract(denomination.multiply(BigDecimal.valueOf(categoriesToDispense)));
            }
            if (remainingAmount.compareTo(BigDecimal.ZERO) == 0) {
                break;
            }
        }

        if (remainingAmount.compareTo(BigDecimal.ZERO) > 0) {
            throw new ValidationException("Cannot dispense the exact amount with available denomination");
        }
        return dispenseCategories;
    }

    @Override
    public BigDecimal checkBalance(String pin) {
        if (pin == null) {
            throw new ValidationException("pin cannot be null!");
        }

        return bankService.checkBalance(pin);
    }

    @Override
    public void transfer(BigDecimal amount, String senderPin, String receiverPin) {
        if (amount == null || senderPin == null || receiverPin == null) {
            throw new ValidationException("amount and sender and receiver pin cannot be null!");
        }

        bankService.transfer(amount, senderPin, receiverPin);
    }


}
