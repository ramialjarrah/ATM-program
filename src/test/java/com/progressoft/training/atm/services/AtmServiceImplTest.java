package com.progressoft.training.atm.services;

import com.progressoft.training.atm.exceptions.ValidationException;
import com.progressoft.training.atm.repositories.AtmRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AtmServiceImplTest {

    private AtmService atmService;

    @Mock
    private BankService bankService;
    private AtmRepository atmRepository;
    @BeforeEach
    void setUp() {

        this.atmService = new AtmServiceImpl(bankService,atmRepository);
    }

    @Test
    void givenCorrectPin_whenCallingCheckBalance_thenReturnBalance() {
        BigDecimal amount = new BigDecimal("100");
        String pin = "1111";

        Mockito.when(bankService.checkBalance(pin)).thenReturn(BigDecimal.valueOf(100));

        assertEquals(amount, atmService.checkBalance(pin));
    }

    @Test
    void givenCorrectAmountAndPin_whenCallingDeposit_thenDeposit() {
        BigDecimal amount = new BigDecimal("100");
        String pin = "12345";

        atmService.deposit(amount, pin);

        Mockito.verify(bankService).deposit(amount, pin);
    }

    @Test
    void givenCorrectAmountAndPin_whenCallingWithdraw_thenWithdraw() {
        BigDecimal amount = new BigDecimal("100");
        String pin = "12345";

        atmService.withdraw(amount, pin);

        Mockito.verify(bankService).withdraw(amount, pin);
    }

    @Test
    void givenCorrectAmountAndPin_whenCallingTransfer_thenTransfer() {
        BigDecimal amount = new BigDecimal("100");
        String senderPin = "12345";
        String receiverPin = "23456";

        atmService.transfer(amount, senderPin, receiverPin);

        Mockito.verify(bankService).transfer(amount, senderPin, receiverPin);
    }

    @Test
    void givenWrongAmountOrPin_whenCallingDeposit_thenDeposit() {
        BigDecimal amount = new BigDecimal("100");
        String pin = "12345";

        Exception ex = assertThrows(ValidationException.class, () -> atmService.deposit(null, pin));
        assertEquals("amount and pin cannot be null!", ex.getMessage());

        Exception ex2 = assertThrows(ValidationException.class, () -> atmService.deposit(amount, null));
        assertEquals("amount and pin cannot be null!", ex.getMessage());


        Exception ex3 = assertThrows(ValidationException.class, () -> atmService.deposit(null, null));
        assertEquals("amount and pin cannot be null!", ex.getMessage());
    }

    @Test
    void givenWrongAmountOrPin_whenCallingWithdraw_thenWithdraw() {
        BigDecimal amount = new BigDecimal("100");
        String pin = "12345";
        Exception ex = assertThrows(ValidationException.class, () -> atmService.withdraw(null, pin));
        assertEquals("amount and pin cannot be null!", ex.getMessage());

        Exception ex2 = assertThrows(ValidationException.class, () -> atmService.withdraw(amount, null));
        assertEquals("amount and pin cannot be null!", ex2.getMessage());

        Exception ex3 = assertThrows(ValidationException.class, ()-> atmService.withdraw(null, null));
        assertEquals("amount and pin cannot be null!", ex3.getMessage());
    }

    @Test
    void givenWrongAmountOrPin_whenCallingTransfer_thenTransfer() {
        String senderPin = "12345";
        String receiverPin = "23456";
        BigDecimal balance = BigDecimal.valueOf(100);

        Exception ex1 = assertThrows(ValidationException.class, ()-> atmService.transfer(balance, null, receiverPin));
        assertEquals("amount and sender and reciever pin cannot be null!", ex1.getMessage());

        Exception ex2 = assertThrows(ValidationException.class, ()-> atmService.transfer(null, senderPin, receiverPin));
        assertEquals("amount and sender and reciever pin cannot be null!", ex2.getMessage());

        Exception ex3 = assertThrows(ValidationException.class, ()-> atmService.transfer(null, null, receiverPin));
        assertEquals("amount and sender and reciever pin cannot be null!", ex3.getMessage());

        Exception ex4 = assertThrows(ValidationException.class, ()-> atmService.transfer(null, senderPin, null));
        assertEquals("amount and sender and reciever pin cannot be null!", ex4.getMessage());

        Exception ex5 = assertThrows(ValidationException.class, ()-> atmService.transfer(null, null, null));
        assertEquals("amount and sender and reciever pin cannot be null!", ex5.getMessage());

        Exception ex6 = assertThrows(ValidationException.class, ()-> atmService.transfer(balance, senderPin, null));
        assertEquals("amount and sender and reciever pin cannot be null!", ex6.getMessage());

        Exception ex7 = assertThrows(ValidationException.class, ()-> atmService.transfer(balance, null, null));
        assertEquals("amount and sender and reciever pin cannot be null!", ex7.getMessage());

    }

    @Test
    void givenWrongPin_whenCallingCheckBalance_thenCheckBalance() {

        Exception ex = assertThrows(ValidationException.class, ()-> atmService.checkBalance(null));
        assertEquals("pin cannot be null!", ex.getMessage());

    }
}