package com.progressoft.training.atm.bank.service.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

class DepositRequestTest {

    private static Validator VALIDATOR;

    @BeforeEach
    void setUp() {
        VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void givenValidDepositRequest_whenCallingValidation_thenReturnTrue(){
        DepositRequest depositRequest = new DepositRequest(BigDecimal.valueOf(500), "1234");
        Set<ConstraintViolation<DepositRequest>> violations = VALIDATOR.validate(depositRequest);
        Assertions.assertTrue(violations.isEmpty());
    }
    @Test
    public void givenNullDepositRequest_whenCallingValidation_thenReturnFalse(){
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> VALIDATOR.validate((DepositRequest) null));
    }
    @Test
    public void givenNullAmount_whenCallingValidation_thenReturnFalse(){
        DepositRequest depositRequest = new DepositRequest(null, "1234");
        Set<ConstraintViolation<DepositRequest>> violations = VALIDATOR.validate(depositRequest);
        Assertions.assertFalse(violations.isEmpty());
    }
    @Test
    public void givenNegativeAmount_whenCallingValidation_thenReturnFalse(){
        DepositRequest depositRequest = new DepositRequest(BigDecimal.valueOf(-500), "1234");
        Set<ConstraintViolation<DepositRequest>> violations = VALIDATOR.validate(depositRequest);
        Assertions.assertFalse(violations.isEmpty());
    }
    @Test
    public void givenNullPin_whenCallingValidation_thenReturnFalse(){
        DepositRequest depositRequest = new DepositRequest(BigDecimal.valueOf(500), null);
        Set<ConstraintViolation<DepositRequest>> violations = VALIDATOR.validate(depositRequest);
        Assertions.assertFalse(violations.isEmpty());
    }
    @Test
    public void givenLongSizePin_whenCallingValidation_thenReturnFalse(){
        DepositRequest depositRequest = new DepositRequest(BigDecimal.valueOf(500), "1234567");
        Set<ConstraintViolation<DepositRequest>> violations = VALIDATOR.validate(depositRequest);
        Assertions.assertFalse(violations.isEmpty());
    }
    @Test
    public void givenShortSizePin_whenCallingValidation_thenReturnFalse(){
        DepositRequest depositRequest = new DepositRequest(BigDecimal.valueOf(500), "12");
        Set<ConstraintViolation<DepositRequest>> violations = VALIDATOR.validate(depositRequest);
        Assertions.assertFalse(violations.isEmpty());
    }
    @Test
    public void givenEmptyPin_whenCallingValidation_thenReturnFalse(){
        DepositRequest depositRequest = new DepositRequest(BigDecimal.valueOf(500), "");
        Set<ConstraintViolation<DepositRequest>> violations = VALIDATOR.validate(depositRequest);
        Assertions.assertFalse(violations.isEmpty());
    }
}