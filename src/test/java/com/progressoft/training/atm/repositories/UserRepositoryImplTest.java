package com.progressoft.training.atm.repositories;

import com.progressoft.training.atm.exceptions.ValidationException;
import com.progressoft.training.atm.services.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryImplTest {
    UserRepositoryImpl userRepository = new UserRepositoryImpl();

    @Test
    void givenNullPin_whenCallingGetUserByPin_thenException() {

        Exception ex = assertThrows(ValidationException.class, () -> userRepository.getUserByPin(null));
        assertEquals("pin is null!", ex.getMessage());
    }

    @Test
    void givenNotFoundPin_whenCallingGetUserByPin_thenException(){
        String pin = "6999";
        Exception ex = assertThrows(ValidationException.class, () -> userRepository.getUserByPin(pin));
        assertEquals("Invalid PIN: no such user found", ex.getMessage());
    }
    @Test
    void givenNullUserName_whenCallingGetUserByUserName_thenException() {

        Exception ex = assertThrows(ValidationException.class, () -> userRepository.getUserByUsername(null));
        assertEquals("username is null!", ex.getMessage());
    }

    @Test
    void givenNullBalanceAndPin_whenCallingUpdateUserBalance_thenException() {
        String pin = "1234";
        BigDecimal balance = new BigDecimal(100);
        Exception ex1 = assertThrows(ValidationException.class, () -> userRepository.updateUserBalance(null, null));
        assertEquals("balance and pin cannot be null!", ex1.getMessage());

        Exception ex2 = assertThrows(ValidationException.class, () -> userRepository.updateUserBalance(pin, null));
        assertEquals("balance and pin cannot be null!", ex2.getMessage());

        Exception ex3 = assertThrows(ValidationException.class, () -> userRepository.updateUserBalance(null, balance));
        assertEquals("balance and pin cannot be null!", ex3.getMessage());

    }

    @Test
    void givenNullPin_whenCallingGetUserBalance_thenException() {
        Exception ex = assertThrows(ValidationException.class, () -> userRepository.getUserBalance(null));
        assertEquals("pin is null!", ex.getMessage());
    }

    @Test
    void givenCorrcetPin_whenCallingGetUserBalance_thenGetUserBalance() {

        assertEquals(BigDecimal.valueOf(500), userRepository.getUserBalance("1111"));
    }

    @Test
    void givenCorrectUsername_whenCallingGetUserByUsername_thenCorrectUsername() {
        User result = userRepository.getUserByUsername("mohammed");
        assertNotNull(result);
        assertEquals("mohammed", result.getUsername());
    }

    @Test
    void givenCorrectPin_whenCallingGetUserBalance_thenCorrectBalance() {
        User result = userRepository.getUserByPin("1111");
        assertNotNull(result);
        assertEquals(500, result.getBalance().intValue());
    }

    @Test
    void givenCorrectPinAndBalance_whenCallingUpdateUserBalance_thenCorrectBalance() {
        User result = userRepository.getUserByPin("1111");
        assertNotNull(result);
        userRepository.updateUserBalance("1111", BigDecimal.valueOf(100));
        assertEquals(100, result.getBalance().intValue());
    }
}