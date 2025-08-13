package com.progressoft.training.atm.services;

import com.progressoft.training.atm.bank.service.BankServiceImpl;
import com.progressoft.training.atm.bank.service.domain.UserDomain;
import com.progressoft.training.atm.bank.service.request.DepositRequest;
import com.progressoft.training.atm.bank.service.request.TransferRequest;
import com.progressoft.training.atm.bank.service.request.WithdrawRequest;
import com.progressoft.training.atm.exceptions.ValidationException;
import com.progressoft.training.atm.bank.repository.BankRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class BankServiceImplTest {

    @Mock
    private BankRepository bankRepository;
    @InjectMocks
    private BankServiceImpl bankService;

    private List<UserDomain> fakeUsers;

    @BeforeEach
    void setUp() {
        fakeUsers = new ArrayList<>(List.of(
                new UserDomain("1234", "Rami", BigDecimal.valueOf(500)),
                new UserDomain("1111", "Mohammed", BigDecimal.valueOf(1000)),
                new UserDomain("23456", "Ali", BigDecimal.valueOf(20000)),
                new UserDomain("34567", "Noor", BigDecimal.valueOf(5000))
        ));
    }

    @Test
    void givenCorrectPin_whenCallingCheckBalance_thenCheckBalance() {
        String pin = "1234";
        BigDecimal balance = BigDecimal.valueOf(500);

        BankRepository bankRepository = Mockito.mock(BankRepository.class);
        Mockito.when(bankRepository.getUserBalance("1234")).thenReturn(fakeUsers.getFirst().getBalance());


        BankServiceImpl bankService = new BankServiceImpl(bankRepository);

        assertEquals(balance, bankService.checkBalance(pin));
    }

    @Test
    void givenCorrectPin_whenCallingDeposit_thenDeposit() {
        String pin = "12345";
        BigDecimal amount = BigDecimal.valueOf(100);

        UserDomain userDomain = Mockito.mock(UserDomain.class);
        Mockito.when(userDomain.getBalance()).thenReturn(BigDecimal.valueOf(500));

        Mockito.when(bankRepository.getUserByPin(pin)).thenReturn(userDomain);

        DepositRequest depositRequest = new DepositRequest(amount, pin);

        bankService.deposit(depositRequest);

        Mockito.verify(bankRepository, Mockito.times(1)).getUserByPin(pin);

        Mockito.verify(bankRepository, Mockito.times(1)).updateUserBalance(userDomain);
    }


    @Test
    void givenCorrectPinAndBalance_whenCallingWithdraw_thenWithdraw() {
        String pin = "12345";
        BigDecimal amount = BigDecimal.valueOf(100);

        UserDomain userDomain = Mockito.mock(UserDomain.class);
        Mockito.when(userDomain.getBalance()).thenReturn(BigDecimal.valueOf(500));

        Mockito.when(bankRepository.getUserByPin(pin)).thenReturn(userDomain);

        Mockito.when(bankRepository.getUserBalance(pin)).thenReturn(amount);

        WithdrawRequest withdrawRequest = new WithdrawRequest(pin, amount);

        bankService.withdraw(withdrawRequest);

        Mockito.verify(bankRepository, Mockito.times(1)).getUserByPin(pin);

        Mockito.verify(bankRepository, Mockito.times(1)).getUserBalance(pin);
    }

    @Test
    void givenNullPin_whenCallingCheckBalance_thenCheckBalance() {

        Exception ex = assertThrows(ValidationException.class, () -> bankService.checkBalance(null));
        assertEquals("pin cannot be null!", ex.getMessage());
    }

    @Test
    void givenNullPinAndBalance_whenCallingDeposit_thenCDeposit() {
        String pin = "12345";
        BigDecimal balance = BigDecimal.valueOf(100);

        DepositRequest depositRequest1 = new DepositRequest(balance, null);
        Exception ex1 = assertThrows(ValidationException.class, () -> bankService.deposit(depositRequest1));
        assertEquals("pin and balance cannot be null!", ex1.getMessage());

        DepositRequest depositRequest2 = new DepositRequest(null, pin);
        Exception ex2 = assertThrows(ValidationException.class, () -> bankService.deposit(depositRequest2));
        assertEquals("pin and balance cannot be null!", ex2.getMessage());

        DepositRequest depositRequest3 = new DepositRequest(null, null);
        Exception ex3 = assertThrows(ValidationException.class, () -> bankService.deposit(depositRequest3));
        assertEquals("pin and balance cannot be null!", ex3.getMessage());
    }


    @Test
    void givenNullPinAndBalance_whenCallingWithdraw_thenWithdraw() {
        String pin = "12345";
        BigDecimal balance = BigDecimal.valueOf(100);

        WithdrawRequest withdrawRequest1 = new WithdrawRequest(pin, null);
        Exception ex1 = assertThrows(ValidationException.class, () -> bankService.withdraw(withdrawRequest1));
        assertEquals("pin and balance cannot be null!", ex1.getMessage());

        WithdrawRequest withdrawRequest2 = new WithdrawRequest(null, balance);
        Exception ex2 = assertThrows(ValidationException.class, () -> bankService.withdraw(withdrawRequest2));
        assertEquals("pin and balance cannot be null!", ex2.getMessage());

        WithdrawRequest withdrawRequest3 = new WithdrawRequest(null, null);
        Exception ex3 = assertThrows(ValidationException.class, () -> bankService.withdraw(withdrawRequest3));
        assertEquals("pin and balance cannot be null!", ex3.getMessage());
    }

    @Test
    void givenNullPinAndBalance_whenCallingTransfer_thenTransfer() {
        String senderPin = "12345";
        String receiverPin = "23456";
        BigDecimal balance = BigDecimal.valueOf(100);

        TransferRequest transferRequest1 = new TransferRequest(balance, null, receiverPin);
        Exception ex1 = assertThrows(ValidationException.class, () -> bankService.transfer(transferRequest1));
        assertEquals("pin and balance cannot be null!", ex1.getMessage());

        TransferRequest transferRequest2 = new TransferRequest(null, senderPin, receiverPin);
        Exception ex2 = assertThrows(ValidationException.class, () -> bankService.transfer(transferRequest2));
        assertEquals("pin and balance cannot be null!", ex2.getMessage());

        TransferRequest transferRequest3 = new TransferRequest(null, null, receiverPin);
        Exception ex3 = assertThrows(ValidationException.class, () -> bankService.transfer(transferRequest3));
        assertEquals("pin and balance cannot be null!", ex3.getMessage());

        TransferRequest transferRequest4 = new TransferRequest(null, senderPin, null);
        Exception ex4 = assertThrows(ValidationException.class, () -> bankService.transfer(transferRequest4));
        assertEquals("pin and balance cannot be null!", ex4.getMessage());

        TransferRequest transferRequest5 = new TransferRequest(null, null, null);
        Exception ex5 = assertThrows(ValidationException.class, () -> bankService.transfer(transferRequest5));
        assertEquals("pin and balance cannot be null!", ex5.getMessage());

        TransferRequest transferRequest6 = new TransferRequest(balance, senderPin, null);
        Exception ex6 = assertThrows(ValidationException.class, () -> bankService.transfer(transferRequest6));
        assertEquals("pin and balance cannot be null!", ex6.getMessage());

        TransferRequest transferRequest7 = new TransferRequest(balance, null, null);
        Exception ex7 = assertThrows(ValidationException.class, () -> bankService.transfer(transferRequest7));
        assertEquals("pin and balance cannot be null!", ex7.getMessage());
    }

    @Test
    void givenSamePin_whenCallingTransfer_thenTransfer() {
        String senderPin = "12345";
        String receiverPin = "12345";
        BigDecimal balance = BigDecimal.valueOf(100);

        TransferRequest transferRequest = new TransferRequest(balance, senderPin, receiverPin);
        Exception ex = assertThrows(ValidationException.class, () -> bankService.transfer(transferRequest));
        assertEquals("Cannot transfer to the same account!", ex.getMessage());
    }

    @Test
    void givenAmountGreaterThanSenderBalance_whenCallingTransfer_thenTransfer() {
        String senderPin = "12345";
        String receiverPin = "23456";
        BigDecimal senderBalance = BigDecimal.valueOf(50);
        BigDecimal amount = BigDecimal.valueOf(60);

        UserDomain senderUserDomain = Mockito.mock(UserDomain.class);
        Mockito.when(senderUserDomain.getBalance()).thenReturn(senderBalance);

        Mockito.when(bankRepository.getUserByPin(senderPin)).thenReturn(senderUserDomain);

        TransferRequest transferRequest = new TransferRequest(amount, senderPin, receiverPin);
        Exception ex = assertThrows(ValidationException.class, () -> bankService.transfer(transferRequest));

        assertEquals("amount cannot be greater than sender balance!", ex.getMessage());

    }

    @Test
    void givenCorrectAmount_whenCallingTransfer_thenTransfer() {
        String senderPin = "1234";
        String receiverPin = "1111";
        BigDecimal amount = BigDecimal.valueOf(30);

        Mockito.when(bankRepository.getUserByPin(senderPin)).thenReturn(fakeUsers.get(0));
        Mockito.when(bankRepository.getUserByPin(receiverPin)).thenReturn(fakeUsers.get(1));

        TransferRequest transferRequest = new TransferRequest(amount,senderPin,receiverPin);
        bankService.transfer(transferRequest);

        Mockito.verify(bankRepository).updateUserBalance(fakeUsers.getFirst());
        Mockito.verify(bankRepository).updateUserBalance(fakeUsers.get(1));
    }

}