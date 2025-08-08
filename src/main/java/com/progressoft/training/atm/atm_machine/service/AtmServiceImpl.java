package com.progressoft.training.atm.atm_machine.service;

import com.progressoft.training.atm.atm_machine.service.domain.CashDomain;
import com.progressoft.training.atm.atm_machine.service.request.DepositRequest;
import com.progressoft.training.atm.atm_machine.service.request.TransferRequest;
import com.progressoft.training.atm.atm_machine.service.request.WithdrawRequest;
import com.progressoft.training.atm.bank.service.domain.UserDomain;
import com.progressoft.training.atm.exceptions.ValidationException;
import com.progressoft.training.atm.atm_machine.repository.AtmRepository;
import com.progressoft.training.atm.bank.service.BankService;

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
    public void deposit(DepositRequest depositRequest) {
        if (depositRequest.cashDomains() == null || depositRequest.pin() == null) {
            throw new ValidationException("amount and pin cannot be null!");
        }
        BigDecimal totalAmount = calculateAmountFromCategories(depositRequest);
        bankService.deposit(new com.progressoft.training.atm.bank.service.request.DepositRequest(totalAmount, depositRequest.pin()));
    }

    private BigDecimal calculateAmountFromCategories(DepositRequest depositRequest) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for(Map.Entry<CashDomain,Integer> entry : depositRequest.cashDomains().entrySet()){
            CashDomain cashDomain = entry.getKey();
            int quantity = entry.getValue();
            totalAmount = totalAmount.add(cashDomain.getCashCount().multiply(BigDecimal.valueOf(quantity)));
            cashDomain.setCashQuantity(cashDomain.getCashQuantity()+(quantity));
            atmRepository.updateCategory(cashDomain);
        }
        return totalAmount;
    }

    @Override
    public void withdraw(WithdrawRequest withdrawRequest) {

        if (withdrawRequest.amount() == null || withdrawRequest.pin() == null) {
            throw new ValidationException("amount and pin cannot be null!");
        }
        if (withdrawRequest.amount().compareTo(atmRepository.getActualAmount()) > 0) {
            throw new ValidationException("Amount in the ATM is less than the amount you want to withdraw!");
        }

        Map<CashDomain,Integer> dispenseCategories = calculateDispenseCategories(withdrawRequest.amount());
        for(Map.Entry<CashDomain,Integer> entry : dispenseCategories.entrySet()){
            CashDomain cashDomain = entry.getKey();
            int quantity = entry.getValue();
            cashDomain.setCashQuantity(cashDomain.getCashQuantity() - quantity);
            atmRepository.updateCategory(cashDomain);
        }
        bankService.withdraw(new com.progressoft.training.atm.bank.service.request.WithdrawRequest(withdrawRequest.pin(), withdrawRequest.amount()));
    }

    private Map<CashDomain,Integer> calculateDispenseCategories(BigDecimal amount) {
        Map<CashDomain,Integer> dispenseCategories = new HashMap<>();
        List<CashDomain> sortedCategories = atmRepository.sortCashCategory();
        BigDecimal remainingAmount = amount;
        for (CashDomain cashDomain : sortedCategories) {

            int availableCategories = cashDomain.getCashQuantity();
            BigDecimal denomination = cashDomain.getCashCount();
            int neededCategories = remainingAmount.divide(denomination, 0, RoundingMode.DOWN).intValue();
            int categoriesToDispense = Math.min(neededCategories, availableCategories);

            if (categoriesToDispense > 0) {
                dispenseCategories.put(cashDomain, categoriesToDispense);
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
    public void transfer(TransferRequest transferRequest) {
        if (transferRequest.amount() == null || transferRequest.senderPin() == null || transferRequest.receiverPin() == null) {
            throw new ValidationException("amount and sender and receiver pin cannot be null!");
        }
        bankService.transfer(new com.progressoft.training.atm.bank.service.request.TransferRequest(transferRequest.amount(), transferRequest.senderPin(), transferRequest.receiverPin()));
    }

    @Override
    public List<CashDomain> retrieveCashes() {
        return atmRepository.retrieveCashes();
    }

    @Override
    public CashDomain getCashById(String cashId) {
        List<CashDomain> cashDomains = atmRepository.retrieveCashes();
        for (CashDomain cashDomain : cashDomains) {
            if (cashDomain.getCashID().toString().equals(cashId)) {
                return cashDomain;
            }
        }
        throw new ValidationException("No cash found with id: "+cashId);
    }

    @Override
    public UserDomain findUserByPin(String pin){
        if(pin == null){
            throw new ValidationException("pin cannot be null!");
        }

        return bankService.getUserByPin(pin);
    }
}
