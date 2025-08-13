package com.progressoft.training.atm.atm_machine.service.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

class TransferRequestTest {

    private static Validator VALIDATOR;

    @BeforeEach
    void setUp() {
        VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void givenValidTransferRequest_whenCallingValidate_thenReturnTrue() {
        TransferRequest transferRequest = new TransferRequest(BigDecimal.valueOf(500), "1234", "1111");
        Set<ConstraintViolation<TransferRequest>> violations = VALIDATOR.validate(transferRequest);
        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    public void givenNullTransferRequest_whenCallingValidate_thenReturnFalse() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> VALIDATOR.validate((TransferRequest) null));
    }

    @Test
    public void givenNullAmount_whenCallingValidate_thenReturnFalse() {
        TransferRequest transferRequest = new TransferRequest(null, "1234", "1111");
        Set<ConstraintViolation<TransferRequest>> violations = VALIDATOR.validate(transferRequest);
        Assertions.assertFalse(violations.isEmpty());
    }
    @Test
    public void givenNegativeAmount_whenCallingValidate_thenReturnFalse() {
        TransferRequest transferRequest = new TransferRequest(BigDecimal.valueOf(-500), "1234", "1111");
        Set<ConstraintViolation<TransferRequest>> violations = VALIDATOR.validate(transferRequest);
        Assertions.assertFalse(violations.isEmpty());
    }
    @Test
    public void givenNullSenderPin_whenCallingValidate_thenReturnFalse() {
        TransferRequest transferRequest = new TransferRequest(BigDecimal.valueOf(500), null, "1111");
        Set<ConstraintViolation<TransferRequest>> violations = VALIDATOR.validate(transferRequest);
        Assertions.assertFalse(violations.isEmpty());
    }
    @Test
    public void givenNullReceiverPin_whenCallingValidate_thenReturnFalse() {
        TransferRequest transferRequest = new TransferRequest(BigDecimal.valueOf(500), "1234", null);
        Set<ConstraintViolation<TransferRequest>> violations = VALIDATOR.validate(transferRequest);
        Assertions.assertFalse(violations.isEmpty());
    }
    @Test
    public void givenLongSizeSenderPin_whenCallingValidate_thenReturnFalse() {
        TransferRequest transferRequest = new TransferRequest(BigDecimal.valueOf(500), "1234567", "1111");
        Set<ConstraintViolation<TransferRequest>> violations = VALIDATOR.validate(transferRequest);
        Assertions.assertFalse(violations.isEmpty());
    }
    @Test
    public void givenLongSizeReceiverPin_whenCallingValidate_thenReturnFalse() {
        TransferRequest transferRequest = new TransferRequest(BigDecimal.valueOf(500), "1234", "1111567");
        Set<ConstraintViolation<TransferRequest>> violations = VALIDATOR.validate(transferRequest);
        Assertions.assertFalse(violations.isEmpty());
    }
    @Test
    public void givenShortSizeSenderPin_whenCallingValidate_thenReturnFalse() {
        TransferRequest transferRequest = new TransferRequest(BigDecimal.valueOf(500), "12", "1111");
        Set<ConstraintViolation<TransferRequest>> violations = VALIDATOR.validate(transferRequest);
        Assertions.assertFalse(violations.isEmpty());
    }
    @Test
    public void givenShortSizeReceiverPin_whenCallingValidate_thenReturnFalse() {
        TransferRequest transferRequest = new TransferRequest(BigDecimal.valueOf(500), "1234", "111");
        Set<ConstraintViolation<TransferRequest>> violations = VALIDATOR.validate(transferRequest);
        Assertions.assertFalse(violations.isEmpty());
    }
}