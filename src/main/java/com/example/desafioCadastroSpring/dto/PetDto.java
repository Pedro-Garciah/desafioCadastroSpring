package com.example.desafioCadastroSpring.dto;

import com.example.desafioCadastroSpring.model.Enums.Sex;
import com.example.desafioCadastroSpring.model.Enums.Type;
import com.example.desafioCadastroSpring.model.Pet;
import com.example.desafioCadastroSpring.utils.ValidBreedConstraint;
import com.example.desafioCadastroSpring.utils.ValidNameConstraint;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record PetDto(

        @ValidNameConstraint
        String name,

        Type type,

        Sex sex,

        AddressDto address,

        @Max(value = 20, message = "O Pet não pode ter mais que 20 anos")
        double age,

        @DecimalMin(value = "0.5", message = "O peso deve estra entre 0.5kg e 60kg")
        @Max(value = 60, message = "O peso deve estra entre 0.5kg e 60kg")
        double weight,

        @ValidBreedConstraint
        String breed) {



    public Pet toPet() {
        return new Pet(
                name,
                type,
                sex,
                address.toAddress(),
                age,
                weight,
                breed);
    }
}
