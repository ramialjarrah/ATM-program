package com.progressoft.training.atm.services;

import com.progressoft.training.atm.exceptions.ValidationException;
import com.progressoft.training.atm.repositories.TransferLogFileRepositoryImpl;
import com.progressoft.training.atm.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class BankServiceImplTest {

    @Mock private UserRepository userRepository;
    @Mock private TransferLogFileRepositoryImpl transferLogFileRepository;
    @InjectMocks
    private BankServiceImpl bankService;

    @BeforeEach
    void setUp() {


    }

    @Test
    void givenCorrectPin_whenCallingCheckBalance_thenCheckBalance() {
        String pin = "12345";
        BigDecimal balance = BigDecimal.valueOf(100);

        UserRepository userRepository = Mockito.mock(UserRepository.class);
        Mockito.when(userRepository.getUserBalance("12345")).thenReturn(balance);

        BankServiceImpl bankService = new BankServiceImpl(userRepository, transferLogFileRepository);

        assertEquals(balance, bankService.checkBalance(pin));
    }

    @Test
    void givenCorrectPin_whenCallingDeposit_thenDeposit() {
        String pin = "12345";
        BigDecimal amount = BigDecimal.valueOf(100);


        Mockito.when(userRepository.getUserBalance(pin)).thenReturn(amount);

        bankService.deposit(amount, pin);

        Mockito.verify(userRepository, Mockito.times(1)).getUserBalance(pin);
    }

    @Test
    void givenCorrectPinAndBalance_whenCallingWithdraw_thenWithdraw() {
        String pin = "12345";
        BigDecimal amount = BigDecimal.valueOf(100);

        Mockito.when(userRepository.getUserBalance(pin)).thenReturn(amount);

        bankService.withdraw(amount, pin);

        Mockito.verify(userRepository, Mockito.times(1)).getUserBalance(pin);
    }

    @Test
    void givenNullPin_whenCallingCheckBalance_thenCheckBalance() {

        Exception ex = assertThrows(ValidationException.class, ()-> bankService.checkBalance(null));
        assertEquals("pin cannot be null!", ex.getMessage());
    }

    @Test
    void givenNullPinAndBalance_whenCallingDeposit_thenCDeposit() {
        String pin = "12345";
        BigDecimal balance = BigDecimal.valueOf(100);

        Exception ex1 = assertThrows(ValidationException.class, ()-> bankService.deposit(balance, null));
        assertEquals("pin and balance cannot be null!", ex1.getMessage());

        Exception ex2 = assertThrows(ValidationException.class, ()-> bankService.deposit(null, pin));
        assertEquals("pin and balance cannot be null!", ex2.getMessage());

        Exception ex3 = assertThrows(ValidationException.class, ()-> bankService.deposit(null, null));
        assertEquals("pin and balance cannot be null!", ex3.getMessage());
    }


    @Test
    void givenNullPinAndBalance_whenCallingWithdraw_thenWithdraw() {
        String pin = "12345";
        BigDecimal balance = BigDecimal.valueOf(100);

        Exception ex1 = assertThrows(ValidationException.class, ()-> bankService.withdraw(balance, null));
        assertEquals("pin and balance cannot be null!", ex1.getMessage());

        Exception ex2 = assertThrows(ValidationException.class, ()-> bankService.withdraw(null, pin));
        assertEquals("pin and balance cannot be null!", ex2.getMessage());

        Exception ex3 = assertThrows(ValidationException.class, ()-> bankService.withdraw(null, null));
        assertEquals("pin and balance cannot be null!", ex3.getMessage());
    }
    @Test
    void givenNullPinAndBalance_whenCallingTransfer_thenTransfer() {
        String senderPin = "12345";
        String receiverPin = "23456";
        BigDecimal balance = BigDecimal.valueOf(100);
        Exception ex1 = assertThrows(ValidationException.class, ()-> bankService.transfer(balance, null, receiverPin));
        assertEquals("pin and balance cannot be null!", ex1.getMessage());

        Exception ex2 = assertThrows(ValidationException.class, ()-> bankService.transfer(null, senderPin, receiverPin));
        assertEquals("pin and balance cannot be null!", ex2.getMessage());

        Exception ex3 = assertThrows(ValidationException.class, ()-> bankService.transfer(null, null, receiverPin));
        assertEquals("pin and balance cannot be null!", ex3.getMessage());

        Exception ex4 = assertThrows(ValidationException.class, ()-> bankService.transfer(null, senderPin, null));
        assertEquals("pin and balance cannot be null!", ex4.getMessage());

        Exception ex5 = assertThrows(ValidationException.class, ()-> bankService.transfer(null, null, null));
        assertEquals("pin and balance cannot be null!", ex5.getMessage());

        Exception ex6 = assertThrows(ValidationException.class, ()-> bankService.transfer(balance, senderPin, null));
        assertEquals("pin and balance cannot be null!", ex6.getMessage());

        Exception ex7 = assertThrows(ValidationException.class, ()-> bankService.transfer(balance, null, null));
        assertEquals("pin and balance cannot be null!", ex7.getMessage());
    }

@Test
    void givenSamePin_whenCallingTransfer_thenTransfer() {
        String senderPin = "12345";
        String receiverPin = "12345";
        BigDecimal balance = BigDecimal.valueOf(100);

        Exception ex = assertThrows(ValidationException.class, ()-> bankService.transfer(balance, senderPin, receiverPin));
        assertEquals("Cannot transfer to the same account!", ex.getMessage());
    }

@Test
    void givenAmountGreaterThanSenderBalance_whenCallingTransfer_thenTransfer() {
        String senderPin = "12345";
        String receiverPin = "23456";
        BigDecimal senderBalance = BigDecimal.valueOf(50);
        BigDecimal amount = BigDecimal.valueOf(60);

        Mockito.when(userRepository.getUserBalance(senderPin)).thenReturn(senderBalance);

        Exception ex = assertThrows(ValidationException.class, ()-> bankService.transfer(amount, senderPin, receiverPin));

        assertEquals("amount cannot be greater than sender balance!", ex.getMessage());

    }

    @Test
    void givenCorrectAmount_whenCallingTransfer_thenTransfer() {
        String senderPin = "12345";
        String receiverPin = "23456";
        BigDecimal senderBalance = BigDecimal.valueOf(50);
        BigDecimal receiverBalance = BigDecimal.valueOf(100);
        BigDecimal amount = BigDecimal.valueOf(30);



        Mockito.when(userRepository.getUserBalance(senderPin)).thenReturn(senderBalance);
        Mockito.when(userRepository.getUserBalance(receiverPin)).thenReturn(receiverBalance);

        bankService.transfer(amount, senderPin, receiverPin);

        Mockito.verify(userRepository).updateUserBalance(senderPin, new BigDecimal("20"));
        Mockito.verify(userRepository).updateUserBalance(receiverPin, new BigDecimal("130"));
    }

}