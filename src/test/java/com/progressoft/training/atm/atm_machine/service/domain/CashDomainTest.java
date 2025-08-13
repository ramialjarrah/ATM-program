package com.progressoft.training.atm.atm_machine.service.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

class CashDomainTest {

    private static Validator VALIDATOR;

    @BeforeEach
    void setUp() {
        VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void givenValidCashDomain_whenCallingValidate_thenReturnTrue() {
        CashDomain cashDomain = new CashDomain("jlsljdskdflsd", "OneDinar", BigDecimal.valueOf(1), 30);
        Set<ConstraintViolation<CashDomain>> violations = VALIDATOR.validate(cashDomain);
        Assertions.assertTrue(violations.isEmpty());
    }
    @Test
    public void givenNullCashDomain_whenCallingValidate_thenReturnTrue(){
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> VALIDATOR.validate((CashDomain) null));
    }
    @Test
    public void givenNullId_whenCallingValidate_thenReturnFalse() {
        CashDomain cashDomain = new CashDomain(null, "OneDinar", BigDecimal.valueOf(1), 30);
        Set<ConstraintViolation<CashDomain>> violations = VALIDATOR.validate(cashDomain);
        Assertions.assertFalse(violations.isEmpty());
    }
    @Test
    public void givenEmptyId_whenCallingValidate_thenReturnFalse() {
        CashDomain cashDomain = new CashDomain("", "OneDinar", BigDecimal.valueOf(1), 30);
        Set<ConstraintViolation<CashDomain>> violations = VALIDATOR.validate(cashDomain);
        Assertions.assertFalse(violations.isEmpty());
    }
    @Test
    public void givenNullName_whenCallingValidate_thenReturnFalse() {
        CashDomain cashDomain = new CashDomain("1234", null, BigDecimal.valueOf(1), 30);
        Set<ConstraintViolation<CashDomain>> violations = VALIDATOR.validate(cashDomain);
        Assertions.assertFalse(violations.isEmpty());
    }
    @Test
    public void givenEmptyName_whenCallingValidate_thenReturnFalse() {
        CashDomain cashDomain = new CashDomain("1234", "", BigDecimal.valueOf(1), 30);
        Set<ConstraintViolation<CashDomain>> violations = VALIDATOR.validate(cashDomain);
        Assertions.assertFalse(violations.isEmpty());
    }
    @Test
    public void givenNullAmount_whenCallingValidate_thenReturnFalse() {
        CashDomain cashDomain = new CashDomain("1234", "OneDinar", null, 30);
        Set<ConstraintViolation<CashDomain>> violations = VALIDATOR.validate(cashDomain);
        Assertions.assertFalse(violations.isEmpty());
    }
    @Test
    public void givenNegativeAmount_whenCallingValidate_thenReturnFalse() {
        CashDomain cashDomain = new CashDomain("1234", "OneDinar", BigDecimal.valueOf(-1), 30);
        Set<ConstraintViolation<CashDomain>> violations = VALIDATOR.validate(cashDomain);
        Assertions.assertFalse(violations.isEmpty());
    }
    @Test
    public void givenNegativeQuantity_whenCallingValidate_thenReturnFalse() {
        CashDomain cashDomain = new CashDomain("1234", "OneDinar", BigDecimal.valueOf(1), -30);
        Set<ConstraintViolation<CashDomain>> violations = VALIDATOR.validate(cashDomain);
        Assertions.assertFalse(violations.isEmpty());
    }
}