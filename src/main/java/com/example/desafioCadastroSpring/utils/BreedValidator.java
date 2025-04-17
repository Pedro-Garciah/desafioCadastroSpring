package com.example.desafioCadastroSpring.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BreedValidator implements ConstraintValidator<ValidBreedConstraint, String> {
    @Override
    public void initialize(ValidBreedConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String breed, ConstraintValidatorContext constraintValidatorContext) {
        return breed.matches("^[A-Za-zÀ-ÿ\\s]+$");
    }
}
