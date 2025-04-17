package com.example.desafioCadastroSpring.dto;

import com.example.desafioCadastroSpring.model.Enums.Sex;
import com.example.desafioCadastroSpring.model.Enums.Type;
import com.example.desafioCadastroSpring.utils.ValidBreedConstraint;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;

public record SearchPetDto(

        String name,

        Type type,

        Sex sex,

        AddressDto address,

        @Max(value = 20, message = "O Pet não pode ter mais que 20 anos")
        Double age,

        @DecimalMin(value = "0.5", message = "O peso deve estra entre 0.5kg e 60kg")
        @Max(value = 60, message = "O peso deve estra entre 0.5kg e 60kg")
        Double weight,

        @ValidBreedConstraint
        String breed) {
}
