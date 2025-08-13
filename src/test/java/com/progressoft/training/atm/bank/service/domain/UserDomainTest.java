package com.progressoft.training.atm.bank.service.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

class UserDomainTest {

    public static Validator VALIDATOR;

    @BeforeEach
    void setUp() {

        VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void givenValidUserDomain_whenCallingValidate_thenReturnTrue() {
        UserDomain userDomain = new UserDomain("1234", "username", BigDecimal.valueOf(500));
        Set<ConstraintViolation<UserDomain>> violations = VALIDATOR.validate(userDomain);
        Assertions.assertTrue(violations.isEmpty());
    }
    @Test
    public void givenNullUserDomain_whenCallingValidate_thenReturnFalse() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> VALIDATOR.validate((UserDomain) null));
    }
    @Test
    public void givenNullPin_whenCallingValidate_thenReturnFalse() {
        UserDomain userDomain = new UserDomain(null, "username", BigDecimal.valueOf(500));
        Set<ConstraintViolation<UserDomain>> violations = VALIDATOR.validate(userDomain);
        Assertions.assertFalse(violations.isEmpty());
    }
    @Test
    public void givenNegativeBalance_whenCallingValidate_thenReturnFalse() {
        UserDomain userDomain = new UserDomain("1234", "username", BigDecimal.valueOf(-500));
        Set<ConstraintViolation<UserDomain>> violations = VALIDATOR.validate(userDomain);
        Assertions.assertFalse(violations.isEmpty());
    }
    @Test
    public void givenNullUsername_whenCallingValidate_thenReturnFalse() {
        UserDomain userDomain = new UserDomain("1234", null, BigDecimal.valueOf(500));
        Set<ConstraintViolation<UserDomain>> violations = VALIDATOR.validate(userDomain);
        Assertions.assertFalse(violations.isEmpty());
    }
    @Test
    public void givenLongSizeUsername_whenCallingValidate_thenReturnFalse() {
        UserDomain userDomain = new UserDomain("1234", "veryVeryLongUsernameThatIsImpossibleThatSomeOneHasThisName", BigDecimal.valueOf(500));
        Set<ConstraintViolation<UserDomain>> violations = VALIDATOR.validate(userDomain);
        Assertions.assertFalse(violations.isEmpty());
    }
    @Test
    public void givenShortSizeUsername_whenCallingValidate_thenReturnFalse() {
        UserDomain userDomain = new UserDomain("1234", "v", BigDecimal.valueOf(500));
        Set<ConstraintViolation<UserDomain>> violations = VALIDATOR.validate(userDomain);
        Assertions.assertFalse(violations.isEmpty());
    }
    @Test
    public void givenNullUuid_whenCallingValidate_thenReturnFalse() {
        UserDomain userDomain = new UserDomain(null, "username", BigDecimal.valueOf(500));
        Set<ConstraintViolation<UserDomain>> violations = VALIDATOR.validate(userDomain);
        Assertions.assertFalse(violations.isEmpty());
    }
    @Test
    public void givenLongSizePin_whenCallingValidate_thenReturnFalse() {
        UserDomain userDomain = new UserDomain("1234567", "username", BigDecimal.valueOf(500));
        Set<ConstraintViolation<UserDomain>> violations = VALIDATOR.validate(userDomain);
        Assertions.assertFalse(violations.isEmpty());
    }
    @Test
    public void givenShortSizePin_whenCallingValidate_thenReturnFalse() {
        UserDomain userDomain = new UserDomain("12", "username", BigDecimal.valueOf(500));
        Set<ConstraintViolation<UserDomain>> violations = VALIDATOR.validate(userDomain);
        Assertions.assertFalse(violations.isEmpty());
    }

}