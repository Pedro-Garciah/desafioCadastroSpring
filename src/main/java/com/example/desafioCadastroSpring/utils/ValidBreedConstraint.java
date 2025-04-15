package com.example.desafioCadastroSpring.utils;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BreedValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidBreedConstraint {
    String message() default "A raçã não pode conter números e caracteres especiais";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
