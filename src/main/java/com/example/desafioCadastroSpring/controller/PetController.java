package com.example.desafioCadastroSpring.controller;

import com.example.desafioCadastroSpring.dto.PetDto;
import com.example.desafioCadastroSpring.model.Pet;
import com.example.desafioCadastroSpring.repository.PetRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetRepository petRepository;

    @PostMapping
    public ResponseEntity savePet(@RequestBody @Valid PetDto petDto){
        Pet pet = petDto.toPet();
        petRepository.save(pet);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
