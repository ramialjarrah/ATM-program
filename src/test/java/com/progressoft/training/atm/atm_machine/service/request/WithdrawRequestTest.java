package com.progressoft.training.atm.atm_machine.service.request;



import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

class WithdrawRequestTest {

    private static Validator VALIDATOR;

    @BeforeEach
    void setUp() {
        VALIDATOR  = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void givenValidWithdrawRequest_whenCallingValidate_thenReturnTrue() {
        WithdrawRequest withdrawRequest = new WithdrawRequest("1234", BigDecimal.valueOf(500));
        Set<ConstraintViolation<WithdrawRequest>> violations = VALIDATOR.validate(withdrawRequest);
        Assertions.assertTrue(violations.isEmpty());
    }
    @Test
    public void givenNullWithdrawRequest_whenCallingValidate_thenReturnFalse() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> VALIDATOR.validate((WithdrawRequest) null));
    }
    @Test
    public void givenNullPin_whenCallingValidate_thenReturnFalse() {
        WithdrawRequest withdrawRequest = new WithdrawRequest(null, BigDecimal.valueOf(500));
        Set<ConstraintViolation<WithdrawRequest>> violations = VALIDATOR.validate(withdrawRequest);
        Assertions.assertFalse(violations.isEmpty());
    }
    @Test
    public void givenNegativeAmount_whenCallingValidate_thenReturnFalse() {
        WithdrawRequest withdrawRequest = new WithdrawRequest("1234", BigDecimal.valueOf(-500));
        Set<ConstraintViolation<WithdrawRequest>> violations = VALIDATOR.validate(withdrawRequest);
        Assertions.assertFalse(violations.isEmpty());
    }
    @Test
    public void givenNullAmount_whenCallingValidate_thenReturnFalse() {
        WithdrawRequest withdrawRequest = new WithdrawRequest("1234", null);
        Set<ConstraintViolation<WithdrawRequest>> violations = VALIDATOR.validate(withdrawRequest);
        Assertions.assertFalse(violations.isEmpty());
    }
    @Test
    public void givenLongSizePin_whenCallingValidate_thenReturnFalse() {
        WithdrawRequest withdrawRequest = new WithdrawRequest("1234567", BigDecimal.valueOf(500));
        Set<ConstraintViolation<WithdrawRequest>> violations = VALIDATOR.validate(withdrawRequest);
        Assertions.assertFalse(violations.isEmpty());
    }
    @Test
    public void givenShortSizePin_whenCallingValidate_thenReturnFalse() {
        WithdrawRequest withdrawRequest = new WithdrawRequest("12", BigDecimal.valueOf(500));
        Set<ConstraintViolation<WithdrawRequest>> violations = VALIDATOR.validate(withdrawRequest);
        Assertions.assertFalse(violations.isEmpty());
    }
}