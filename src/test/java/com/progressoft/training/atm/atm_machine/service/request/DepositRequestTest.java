package com.progressoft.training.atm.atm_machine.service.request;

import com.progressoft.training.atm.atm_machine.service.domain.CashDomain;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

class DepositRequestTest {

    public static Validator VALIDATOR;

    @BeforeEach
    void setUp() {
        VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void givenValidDepositRequest_whenCallingValidate_thenReturnTrue() {
        Map<CashDomain,Integer> cashDomains = new HashMap<>(Map.of(
                new CashDomain(UUID.randomUUID().toString(), "FiftyDinars", BigDecimal.valueOf(50), 1),1,
                new CashDomain(UUID.randomUUID().toString(), "FiveDinars", BigDecimal.valueOf(5), 1),1,
                new CashDomain(UUID.randomUUID().toString(), "OneDinar", BigDecimal.valueOf(1), 1), 5
        ));
        DepositRequest depositRequest = new DepositRequest("1234",cashDomains);
        Set<ConstraintViolation<DepositRequest>> violations = VALIDATOR.validate(depositRequest);
        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    public void givenNullDepositRequest_whenCallingValidate_thenReturnFalse() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> VALIDATOR.validate((DepositRequest) null));
    }

    @Test
    public void givenEmptyCashDomains_whenCallingValidate_thenReturnFalse() {
        DepositRequest depositRequest = new DepositRequest("1234",new HashMap<>());
        Set<ConstraintViolation<DepositRequest>> violations = VALIDATOR.validate(depositRequest);
        Assertions.assertFalse(violations.isEmpty());
    }

    @Test
    public void givenNullCashDomains_whenCallingValidate_thenReturnFalse() {
        DepositRequest depositRequest = new DepositRequest("1234",null);
        Set<ConstraintViolation<DepositRequest>> violations = VALIDATOR.validate(depositRequest);
        Assertions.assertFalse(violations.isEmpty());
    }

    @Test
    public void givenNullPin_whenCallingValidate_thenReturnFalse() {
        Map<CashDomain,Integer> cashDomains = new HashMap<>(Map.of(
                new CashDomain(UUID.randomUUID().toString(), "FiftyDinars", BigDecimal.valueOf(50), 1),1,
                new CashDomain(UUID.randomUUID().toString(), "FiveDinars", BigDecimal.valueOf(5), 1),1,
                new CashDomain(UUID.randomUUID().toString(), "OneDinar", BigDecimal.valueOf(1), 1), 5
        ));
        DepositRequest depositRequest = new DepositRequest(null,cashDomains);
        Set<ConstraintViolation<DepositRequest>> violations = VALIDATOR.validate(depositRequest);
        Assertions.assertFalse(violations.isEmpty());
    }
}