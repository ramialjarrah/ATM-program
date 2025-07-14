package com.progressoft.training.atm.services;

import com.progressoft.training.atm.exceptions.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(); // Replace with real constructor if needed
    }
    @Test
    public void givenNullUserName_whenCallingSetUserName_thenGiveException(){

        Exception ex = assertThrows(ValidationException.class, () -> user.setUsername(null));
        assertEquals("Username cannot be null!", ex.getMessage());
    }

    @Test
    public void givenNullPin_whenCallingSetUserPin_thenGiveException(){
        Exception ex = assertThrows(ValidationException.class, () -> user.setPin(null));
        assertEquals("Pin cannot be null!", ex.getMessage());
    }

    @Test
    public void givenNullBalance_whenCallingSetUserBalance_thenGiveException(){
        Exception ex = assertThrows(ValidationException.class, () -> user.setBalance(null));
        assertEquals("Balance cannot be null!", ex.getMessage());
    }

    @Test
    public void givenUsername_whenCallingGetUsername_thenReturnUsername() {
        String username = "username";
        user.setUsername(username);
        assertEquals(username, user.getUsername() );
    }

    @Test
    public void givenPin_whenCallingGetPin_thenReturnPin() {
        String pin = "pin";
        user.setPin(pin);
        assertEquals(pin, user.getPin());
    }
    @Test
    public void givenBalance_whenCallingGetBalance_thenReturnBalance() {
        BigDecimal balance = new BigDecimal("20");
        user.setBalance(balance);
        assertEquals(balance, user.getBalance());
    }

    @Test
    public void givenBalance_whenCallingSetBalance_thenSetBalance() {
        BigDecimal balance = new BigDecimal("130");
        user.setBalance(balance);
        assertEquals(balance, user.getBalance());
    }

    @Test
    public void givenUsername_whenCallingSetUsername_thenSetUsername() {
        String username = "username";
        user.setUsername(username);
        assertEquals(username, user.getUsername());
    }

    @Test
    public void givenPin_whenCallingSetPin_thenSetPin() {
        String pin = "pin";
        user.setPin(pin);
        assertEquals(pin, user.getPin());
    }
}