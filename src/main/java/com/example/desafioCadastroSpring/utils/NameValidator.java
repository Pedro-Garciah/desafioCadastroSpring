package com.example.desafioCadastroSpring.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NameValidator implements ConstraintValidator<ValidNameConstraint, String> {
    private static final Logger log = LoggerFactory.getLogger(NameValidator.class);

    @Override
    public void initialize(ValidNameConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return name != null
                && !name.trim().isEmpty()
                && name.trim().split("\\s+").length >= 2
                && name.trim().matches("^[A-Za-zÀ-ÿ\\s]+$");
    }
}
