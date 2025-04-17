package com.example.desafioCadastroSpring.service;

import com.example.desafioCadastroSpring.dto.PetDto;
import com.example.desafioCadastroSpring.model.Address;
import com.example.desafioCadastroSpring.model.Pet;
import com.example.desafioCadastroSpring.repository.PetRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet updatePet(Long id, PetDto petDto) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Não encontrado"));

        Address address = pet.getAddress();

        pet.setName(petDto.name());
        pet.setType(petDto.type());
        pet.setSex(petDto.sex());
        pet.setAge(petDto.age());
        pet.setWeight(petDto.weight());
        pet.setBreed(petDto.breed());

        address.setNumber(petDto.address().number());
        address.setStreet(petDto.address().street());
        address.setCity(petDto.address().city());

        pet.setAddress(address);

        return petRepository.save(pet);
    }
}
