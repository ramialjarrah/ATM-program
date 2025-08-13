package com.progressoft.training.atm.bank.repository.entities;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

class UserEntityTest {

    private static Validator VALIDATOR;


    @BeforeEach
    public void setUp() {
        VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void givenValidUserEntity_whenCallingValidate_thenReturnTrue() {
        UserEntity userEntity = new UserEntity("ldkjflsdjsl", "1234","username", BigDecimal.valueOf(500));
        Set<ConstraintViolation<UserEntity>> violations = VALIDATOR.validate(userEntity);
        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    public void givenNegativeBalance_whenCallingValidate_thenReturnFalse() {
        UserEntity userEntity = new UserEntity("ldkjflsdjsl", "1234","username", BigDecimal.valueOf(-500));
        Set<ConstraintViolation<UserEntity>> violations = VALIDATOR.validate(userEntity);
        Assertions.assertFalse(violations.isEmpty());
    }

    @Test
    public void givenNullPin_whenCallingValidate_thenReturnFalse() {
        Set<ConstraintViolation<UserEntity>> violations = VALIDATOR.validate(new UserEntity("dsldslkjflsldsjf", null,"username", BigDecimal.valueOf(500)));
        Assertions.assertFalse(violations.isEmpty());
    }

    @Test
    public void giveLongSizePin_whenCallingValidate_thenReturnFalse() {
        Set<ConstraintViolation<UserEntity>> violations = VALIDATOR.validate(new UserEntity("dsldslkjflsldsjf", "1234567","username", BigDecimal.valueOf(500)));
        Assertions.assertFalse(violations.isEmpty());
    }

    @Test
    public void givenShortSizePin_whenCallingValidate_thenReturnFalse() {
        Set<ConstraintViolation<UserEntity>> violations = VALIDATOR.validate(new UserEntity("dsldslkjflsldsjf", "12","username", BigDecimal.valueOf(500)));
        Assertions.assertFalse(violations.isEmpty());
    }

    @Test
    public void givenNullUsername_whenCallingValidate_thenReturnFalse() {
        Set<ConstraintViolation<UserEntity>> violations = VALIDATOR.validate(new UserEntity("dsldslkjflsldsjf", "1234","", BigDecimal.valueOf(500)));
        Assertions.assertFalse(violations.isEmpty());
    }

    @Test
    public void givenLongSizeUsername_whenCallingValidate_thenReturnFalse() {
        Set<ConstraintViolation<UserEntity>> violations = VALIDATOR.validate(new UserEntity("dsldslkjflsldsjf", "1234","veryVeryLongUsernameThatIsImpossibleThatSomeOneHasThisName", BigDecimal.valueOf(500)));
        Assertions.assertFalse(violations.isEmpty());
    }
    @Test
    public void givenShortSizeUsername_whenCallingValidate_thenReturnFalse() {
        Set<ConstraintViolation<UserEntity>> violations = VALIDATOR.validate(new UserEntity("dsldslkjflsldsjf", "1234","v", BigDecimal.valueOf(500)));
        Assertions.assertFalse(violations.isEmpty());
    }

    @Test
    public void givenNullUuid_whenCallingValidate_thenReturnFalse() {
        Set<ConstraintViolation<UserEntity>> violations = VALIDATOR.validate(new UserEntity(null, "1234","username", BigDecimal.valueOf(500)));
        Assertions.assertFalse(violations.isEmpty());
    }

}