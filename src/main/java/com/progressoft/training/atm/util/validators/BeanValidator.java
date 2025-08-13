package com.progressoft.training.atm.util.validators;

import jakarta.validation.*;

import java.util.Set;
import java.util.stream.Collectors;

public class BeanValidator {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static void validate(Object object) {
        Set<ConstraintViolation<Object>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            String errorMessages = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));
            throw new ValidationException(errorMessages);
        }
    }

}
