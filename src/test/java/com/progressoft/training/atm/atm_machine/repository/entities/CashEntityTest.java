package com.progressoft.training.atm.atm_machine.repository.entities;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

class CashEntityTest {

    private static Validator VALIDATOR;

    @BeforeEach
    void set(){
        VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void givenValidCashEntity_whenCallingValidate_thenReturnTrue(){
        CashEntity cashEntity = new CashEntity("sldkfjldsfjs", "OneDinar", BigDecimal.valueOf(1), 40);
        Set<ConstraintViolation<CashEntity>> violations = VALIDATOR.validate(cashEntity);
        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    public void givenNullCashEntity_whenCallingValidate_thenReturnFalse(){
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> VALIDATOR.validate((CashEntity) null));
    }
    @Test
    public void givenNullName_whenCallingValidate_thenReturnFalse(){
        CashEntity cashEntity = new CashEntity("sldkfjldsfjs", null, BigDecimal.valueOf(1), 40);
        Set<ConstraintViolation<CashEntity>> violations = VALIDATOR.validate(cashEntity);
        Assertions.assertFalse(violations.isEmpty());
    }
    @Test
    public void givenEmptyName_whenCallingValidate_thenReturnFalse(){
        CashEntity cashEntity = new CashEntity("sldkfjldsfjs", "", BigDecimal.valueOf(1), 40);
        Set<ConstraintViolation<CashEntity>> violations = VALIDATOR.validate(cashEntity);
        Assertions.assertFalse(violations.isEmpty());
    }
    @Test
    public void givenNullAmount_whenCallingValidate_thenReturnFalse(){
        CashEntity cashEntity = new CashEntity("sldkfjldsfjs", "OneDinar", null, 40);
        Set<ConstraintViolation<CashEntity>> violations = VALIDATOR.validate(cashEntity);
        Assertions.assertFalse(violations.isEmpty());
    }
    @Test
    public void givenNegativeAmount_whenCallingValidate_thenReturnFalse(){
        CashEntity cashEntity = new CashEntity("sldkfjldsfjs", "OneDinar", BigDecimal.valueOf(-1), 40);
        Set<ConstraintViolation<CashEntity>> violations = VALIDATOR.validate(cashEntity);
        Assertions.assertFalse(violations.isEmpty());
    }
    @Test
    public void givenNullId_whenCallingValidate_thenReturnFalse(){
        CashEntity cashEntity = new CashEntity(null, "OneDinar", BigDecimal.valueOf(1), 40);
        Set<ConstraintViolation<CashEntity>> violations = VALIDATOR.validate(cashEntity);
        Assertions.assertFalse(violations.isEmpty());
    }
}