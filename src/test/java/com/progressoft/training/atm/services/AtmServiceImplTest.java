//package com.progressoft.training.atm.services;
//
//import com.progressoft.training.atm.atm_machine.service.AtmService;
//import com.progressoft.training.atm.atm_machine.service.AtmServiceImpl;
//
//import com.progressoft.training.atm.atm_machine.service.domain.CashDomain;
//import com.progressoft.training.atm.atm_machine.service.request.DepositRequest;
//import com.progressoft.training.atm.atm_machine.service.request.TransferRequest;
//import com.progressoft.training.atm.atm_machine.service.request.WithdrawRequest;
//import com.progressoft.training.atm.bank.service.BankService;
//import com.progressoft.training.atm.exceptions.ValidationException;
//import com.progressoft.training.atm.atm_machine.repository.AtmRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//
//import static org.mockito.Mockito.when;
//
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.math.BigDecimal;
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//class AtmServiceImplTest {
//
//    private AtmService atmService;
//
//    @Mock
//    private BankService bankService;
//
//    @Mock
//    private AtmRepository atmRepository;
//
//    @BeforeEach
//    void setUp() {
//        this.atmService = new AtmServiceImpl(bankService, atmRepository);
//    }
//
//    @Test
//    void givenCorrectPin_whenCallingCheckBalance_thenReturnBalance() {
//        BigDecimal amount = new BigDecimal("100");
//        String pin = "1111";
//
//        Mockito.when(bankService.checkBalance(pin)).thenReturn(BigDecimal.valueOf(100));
//
//        assertEquals(amount, atmService.checkBalance(pin));
//    }
//
//
//    @Test
//    void givenCorrectCategoriesAndPin_whenCallingDeposit_thenDeposit() {
//
//        Map<CashDomain,Integer> cashDomains = new HashMap<>(Map.of(
//                new CashDomain(UUID.randomUUID(), "FiftyDinars", BigDecimal.valueOf(50), 1),1,
//                new CashDomain(UUID.randomUUID(), "FiveDinars", BigDecimal.valueOf(5), 1),1,
//                new CashDomain(UUID.randomUUID(), "OneDinar", BigDecimal.valueOf(1), 1), 2
//        ));
//        String pin = "12345";
//        DepositRequest depositRequestAtm = new DepositRequest(pin, cashDomains);
//        BigDecimal totalAmount = BigDecimal.ZERO;
//
//        for(Map.Entry<CashDomain,Integer> entry : depositRequestAtm.cashDomains().entrySet()){
//            CashDomain cashDomain = entry.getKey();
//            int quantity = entry.getValue();
//            totalAmount = totalAmount.add(cashDomain.getCashCount().multiply(BigDecimal.valueOf(quantity)));
//            cashDomain.setCashQuantity(cashDomain.getCashQuantity()+(quantity));
//            atmRepository.updateCategory(cashDomain);
//        }
//        atmService.deposit(depositRequestAtm);
//        Mockito.verify(bankService).deposit(new com.progressoft.training.atm.bank.service.request.DepositRequest(totalAmount, depositRequestAtm.pin()));
//    }
//
//    @Test
//    void givenCorrectAmountAndPin_whenCallingWithdraw_thenWithdraw() {
//        BigDecimal amount = new BigDecimal("50");
//        String pin = "1234";
//        WithdrawRequest withdrawRequestAtm = new WithdrawRequest(pin, amount);
//
//        atmService.withdraw(withdrawRequestAtm);
//
//        Mockito.verify(bankService).withdraw(new com.progressoft.training.atm.bank.service.request.WithdrawRequest(withdrawRequestAtm.pin(), withdrawRequestAtm.amount()));
//    }
//
////    @Test
////    void givenCorrectAmountAndPin_whenCallingTransfer_thenTransfer() {
////        BigDecimal amount = new BigDecimal("100");
////        String senderPin = "12345";
////        String receiverPin = "23456";
////        TransferRequest transferRequestAtm = new TransferRequest(amount, senderPin, receiverPin);
////        atmService.transfer(transferRequestAtm);
////
////        Mockito.verify(bankService).transfer(new com.progressoft.training.atm.bank.service.request.TransferRequest(transferRequestAtm.amount(), transferRequestAtm.senderPin(), transferRequestAtm.receiverPin()));
////    }
////
////    @Test
////    void givenWrongAmountOrPin_whenCallingDeposit_thenDeposit() {
////        BigDecimal amount = new BigDecimal("100");
////        String pin = "12345";
////
////        DepositRequest depositRequest1 = new DepositRequest(null, amount);
////        Exception ex = assertThrows(ValidationException.class, () -> atmService.deposit(depositRequest1));
////        assertEquals("amount and pin cannot be null!", ex.getMessage());
////        DepositRequest depositRequest2 = new DepositRequest(pin, null);
////        Exception ex2 = assertThrows(ValidationException.class, () -> atmService.deposit(depositRequest2));
////        assertEquals("amount and pin cannot be null!", ex2.getMessage());
////
////        DepositRequest depositRequest3 = new DepositRequest(null, null);
////        Exception ex3 = assertThrows(ValidationException.class, () -> atmService.deposit(depositRequest3));
////        assertEquals("amount and pin cannot be null!", ex3.getMessage());
////    }
////
////    @Test
////    void givenWrongAmountOrPin_whenCallingWithdraw_thenWithdraw() {
////        BigDecimal amount = new BigDecimal("100");
////        String pin = "12345";
////        WithdrawRequest withdrawRequest1 = new WithdrawRequest(null, amount);
////        Exception ex = assertThrows(ValidationException.class, () -> atmService.withdraw(withdrawRequest1));
////        assertEquals("amount and pin cannot be null!", ex.getMessage());
////
////        WithdrawRequest withdrawRequest2 = new WithdrawRequest(pin, null);
////        Exception ex2 = assertThrows(ValidationException.class, () -> atmService.withdraw(withdrawRequest2));
////        assertEquals("amount and pin cannot be null!", ex2.getMessage());
////
////        WithdrawRequest withdrawRequest3 = new WithdrawRequest(null, null);
////        Exception ex3 = assertThrows(ValidationException.class, () -> atmService.withdraw(withdrawRequest3));
////        assertEquals("amount and pin cannot be null!", ex3.getMessage());
////    }
////
////    @Test
////    void givenWrongAmountOrPin_whenCallingTransfer_thenTransfer() {
////        String senderPin = "12345";
////        String receiverPin = "23456";
////        BigDecimal amount = BigDecimal.valueOf(100);
////
////        TransferRequest transferRequest1 = new TransferRequest(amount, null, receiverPin);
////        Exception ex1 = assertThrows(ValidationException.class, () -> atmService.transfer(transferRequest1));
////        assertEquals("amount and sender and receiver pin cannot be null!", ex1.getMessage());
////
////        TransferRequest transferRequest2 = new TransferRequest(null, senderPin, receiverPin);
////        Exception ex2 = assertThrows(ValidationException.class, () -> atmService.transfer(transferRequest2));
////        assertEquals("amount and sender and receiver pin cannot be null!", ex2.getMessage());
////
////        TransferRequest transferRequest3 = new TransferRequest(null, null, receiverPin);
////        Exception ex3 = assertThrows(ValidationException.class, () -> atmService.transfer(transferRequest3));
////        assertEquals("amount and sender and receiver pin cannot be null!", ex3.getMessage());
////
////        TransferRequest transferRequest4 = new TransferRequest(null, senderPin, null);
////        Exception ex4 = assertThrows(ValidationException.class, () -> atmService.transfer(transferRequest4));
////        assertEquals("amount and sender and receiver pin cannot be null!", ex4.getMessage());
////
////        TransferRequest transferRequest5 = new TransferRequest(null, null, null);
////        Exception ex5 = assertThrows(ValidationException.class, () -> atmService.transfer(transferRequest5));
////        assertEquals("amount and sender and receiver pin cannot be null!", ex5.getMessage());
////
////        TransferRequest transferRequest6 = new TransferRequest(amount, senderPin, null);
////        Exception ex6 = assertThrows(ValidationException.class, () -> atmService.transfer(transferRequest6));
////        assertEquals("amount and sender and receiver pin cannot be null!", ex6.getMessage());
////
////        TransferRequest transferRequest7 = new TransferRequest(amount, null, null);
////        Exception ex7 = assertThrows(ValidationException.class, () -> atmService.transfer(transferRequest7));
////        assertEquals("amount and sender and receiver pin cannot be null!", ex7.getMessage());
////
////    }
////
////    @Test
////    void givenWrongPin_whenCallingCheckBalance_thenCheckBalance() {
////
////        Exception ex = assertThrows(ValidationException.class, () -> atmService.checkBalance(null));
////        assertEquals("pin cannot be null!", ex.getMessage());
////
////    }
//}