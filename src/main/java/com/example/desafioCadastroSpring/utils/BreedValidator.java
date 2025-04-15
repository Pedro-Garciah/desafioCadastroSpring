package com.example.desafioCadastroSpring.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BreedValidator implements ConstraintValidator<ValidNameConstraint, String> {
    @Override
    public void initialize(ValidNameConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String breed, ConstraintValidatorContext constraintValidatorContext) {
        return breed.matches("^[A-Za-zÀ-ÿ\\s]+$");
    }
}
