package com.example.desafioCadastroSpring.dto;

import com.example.desafioCadastroSpring.model.Enums.Sex;
import com.example.desafioCadastroSpring.model.Enums.Type;
import com.example.desafioCadastroSpring.model.Pet;

public record PetDto(
        String name,
        Type type,
        Sex sex,
        AddressDto address,
        int age,
        int weightInGrams) {

    public Pet toPet() {
        return new Pet(
                name,
                type,
                sex,
                address.toAddress(),
                age,
                weightInGrams);
    }
}
