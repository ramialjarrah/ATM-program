package com.progressoft.training.atm;

import com.progressoft.training.atm.repositories.*;
import com.progressoft.training.atm.services.AtmService;
import com.progressoft.training.atm.services.AtmServiceImpl;
import com.progressoft.training.atm.services.BankService;
import com.progressoft.training.atm.services.BankServiceImpl;

import java.math.BigDecimal;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    AtmService atmService;

    public Main( AtmService atmService) {
        this.atmService = atmService;
    }

    public void start(){
        atmService.transfer(BigDecimal.valueOf(50), "2222","1111");
    }
    public static void main(String[] args) {
        AtmRepository atmRepository = new AtmRepositoryImpl();
        UserRepository userRepository = new UserRepositoryImpl();
        TransferLogFileRepository transferLogFileRepository = new TransferLogFileRepositoryImpl();
        BankService bankService1 = new BankServiceImpl(userRepository,transferLogFileRepository);
        AtmService atmService1 = new AtmServiceImpl(bankService1,atmRepository);

        new Main(atmService1).start();


    }
}