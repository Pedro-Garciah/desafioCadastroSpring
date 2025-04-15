package com.example.desafioCadastroSpring.utils;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NameValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidNameConstraint {
    String message() default "Insira nome e sobrenome";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
