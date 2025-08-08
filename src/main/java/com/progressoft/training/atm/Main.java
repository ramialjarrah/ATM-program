package com.progressoft.training.atm;

import com.progressoft.training.atm.atm_machine.mapper.CashEntityDomainMapper;
import com.progressoft.training.atm.atm_machine.mapper.CashEntityDomainMapperImpl;
import com.progressoft.training.atm.atm_machine.repository.AtmRepository;
import com.progressoft.training.atm.atm_machine.repository.AtmRepositoryImpl;
import com.progressoft.training.atm.atm_machine.service.AtmService;
import com.progressoft.training.atm.atm_machine.service.AtmServiceImpl;
import com.progressoft.training.atm.atm_machine.service.domain.CashDomain;
import com.progressoft.training.atm.atm_machine.service.request.DepositRequest;
import com.progressoft.training.atm.atm_machine.service.request.TransferRequest;
import com.progressoft.training.atm.atm_machine.service.request.WithdrawRequest;
import com.progressoft.training.atm.bank.mapper.UserEntityDomainMapper;
import com.progressoft.training.atm.bank.mapper.UserEntityDomainMapperImpl;
import com.progressoft.training.atm.bank.repository.BankRepository;
import com.progressoft.training.atm.bank.repository.BankRepositoryImpl;
import com.progressoft.training.atm.bank.service.BankService;
import com.progressoft.training.atm.bank.service.BankServiceImpl;
import com.progressoft.training.atm.bank.service.domain.UserDomain;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.math.BigDecimal;
import java.util.*;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    CashEntityDomainMapper cashEntityDomainMapper;
    UserEntityDomainMapper userEntityDomainMapper;
    AtmRepository atmRepository;
    AtmService atmService;
    SessionFactory sessionFactory;
    Session session;
    BankRepository bankRepository;
    BankService bankService;
    Scanner scanner;


    public Main() {
        this.cashEntityDomainMapper = new CashEntityDomainMapperImpl();
        this.userEntityDomainMapper = new UserEntityDomainMapperImpl();
        sessionFactory = new Configuration().configure().buildSessionFactory();
        session = sessionFactory.openSession();
        this.atmRepository = new AtmRepositoryImpl(cashEntityDomainMapper, session);
        this.bankRepository = new BankRepositoryImpl(userEntityDomainMapper, session);
        this.bankService = new BankServiceImpl(bankRepository);
        this.atmService = new AtmServiceImpl(bankService, atmRepository);
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        Main mm = new Main();
        mm.start();
    }

    void start() {
        int option;
        do {
            String pin = logIn();
            option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    deposit(pin);
                    break;
                case 2:
                    withdraw(pin);
                    break;
                case 3:
                    System.out.println("Your balance is: " + atmService.checkBalance(pin));
                    break;
                case 4:
                    transferring(pin);
                    break;
                case 5:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option!");
                    break;
            }
        } while (option != 5);
    }

    private String logIn() {
        System.out.print("Please enter your pin to Login: ");
        String pin = scanner.nextLine();
        UserDomain userDomain = atmService.findUserByPin(pin);
        System.out.println("Welcome to ATM machine " + userDomain.getUsername() + "!");
        System.out.println("Your balance is: " + userDomain.getBalance() + " JOD");

        System.out.println("Please select an option:");
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("3. Check Balance");
        System.out.println("4. Transfer");
        System.out.println("5. Exit");
        return pin;
    }

    private void transferring(String pin) {
        System.out.println("Please enter account number of the receiver: ");
        String receiverPin = scanner.nextLine();
        System.out.println("Please enter the amount you want to transfer: ");
        BigDecimal amount = new BigDecimal(scanner.nextLine());
        atmService.transfer(new TransferRequest(amount, pin, receiverPin));
        System.out.println("Transferred Successfully! your balance is: " + atmService.checkBalance(pin) + " JOD");
    }

    private void withdraw(String pin) {
        System.out.println("Please enter the amount you want to withdraw:");

        atmService.withdraw(new WithdrawRequest(pin, new BigDecimal(scanner.nextLine())));
        System.out.println("Successful withdrawal! your balance: " + atmService.checkBalance(pin) + " JOD");
    }

    private void deposit(String pin) {
        System.out.println("Please enter the cash ID and the quantity you want to deposit:");
        printCashes();
        Map<CashDomain,Integer> cashDomains = new HashMap<>();

        String answer;
        do {
            System.out.print("Cash ID: ");
            String cashId = scanner.nextLine();
            System.out.print("Quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();
            cashDomains.put(atmService.getCashById(cashId),quantity);
            System.out.println("Do you want to add another cash? (y/n): ");
            answer = scanner.nextLine();
        }while (!answer.equals("n"));

        atmService.deposit(new DepositRequest(pin, cashDomains));
        System.out.println("Successful deposit! your balance: " + atmService.checkBalance(pin) + " JOD");
    }

    private void printCashes() {
        List <CashDomain> cashes = atmService.retrieveCashes();
        for (CashDomain cashDomain : cashes) {
            System.out.println(cashDomain.getCashID()+" " + cashDomain.getCashCount() + " JOD");
        }
    }
}