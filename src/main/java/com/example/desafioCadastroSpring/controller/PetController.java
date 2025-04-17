package com.example.desafioCadastroSpring.controller;

import com.example.desafioCadastroSpring.dto.PetDto;
import com.example.desafioCadastroSpring.dto.SearchPetDto;
import com.example.desafioCadastroSpring.model.Pet;
import com.example.desafioCadastroSpring.repository.PetRepository;
import com.example.desafioCadastroSpring.service.PetService;
import com.example.desafioCadastroSpring.specification.PetSpecification;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    //    private static final Logger log = LoggerFactory.getLogger(NameValidator.class);
    @Autowired
    private final PetRepository petRepository;
    private final PetService petService;

    public PetController(PetRepository petRepository, PetService petService) {
        this.petRepository = petRepository;
        this.petService = petService;
    }

    @PostMapping
    public ResponseEntity savePet(@RequestBody @Valid PetDto petDto) {
        Pet pet = petDto.toPet();
        petRepository.save(pet);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity updatePet(@PathVariable Long id, @RequestBody @Valid PetDto petDto) {
        return ResponseEntity.ok(petService.updatePet(id, petDto));
    }

    @PostMapping("/search")
    public ResponseEntity searchPets(@RequestBody SearchPetDto dto) {

        Specification<Pet> spec = Specification.where(PetSpecification.nameContains(dto.name())
                .and(PetSpecification.hasType(dto.type()))
                .and(PetSpecification.hasSex(dto.sex()))
                .and(PetSpecification.addressContains(dto.address()))
                .and(PetSpecification.ageEquals(dto.age()))
                .and(PetSpecification.weightEquals(dto.weight()))
                .and(PetSpecification.breedContains(dto.breed())));

        List<Pet> all = petRepository.findAll(spec);
        return ResponseEntity.ok(all);
    }
}
