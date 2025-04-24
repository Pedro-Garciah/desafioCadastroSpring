package com.example.desafioCadastroSpring.service;

import com.example.desafioCadastroSpring.dto.PetDto;
import com.example.desafioCadastroSpring.dto.SearchPetDto;
import com.example.desafioCadastroSpring.model.Address;
import com.example.desafioCadastroSpring.model.Pet;
import com.example.desafioCadastroSpring.repository.PetRepository;
import com.example.desafioCadastroSpring.specification.PetSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet savePet(PetDto petDto){
        Pet pet = petDto.toPet();
        return petRepository.save(pet);
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

    public List<Pet> getAllPets(){
        return petRepository.findAll();
    }

    public Pet getPetById(Long id){
        return petRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public void deletePet(Long id){
        Optional<Pet> petOpt = petRepository.findById(id);
        petOpt.ifPresent(pet -> petRepository.deleteById(pet.getId()));
    }

    public List<Pet> searchPetByParameter(SearchPetDto dto){
        Specification<Pet> petSpecs = Specification.where(PetSpecification.nameContains(dto.name())
                .and(PetSpecification.hasType(dto.type()))
                .and(PetSpecification.hasSex(dto.sex()))
                .and(PetSpecification.addressContains(dto.address()))
                .and(PetSpecification.ageEquals(dto.age()))
                .and(PetSpecification.weightEquals(dto.weight()))
                .and(PetSpecification.breedContains(dto.breed())));

        return petRepository.findAll(petSpecs);
    }
}
