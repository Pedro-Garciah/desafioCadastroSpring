package com.example.desafioCadastroSpring.controller;

import com.example.desafioCadastroSpring.dto.PetDto;
import com.example.desafioCadastroSpring.dto.SearchPetDto;
import com.example.desafioCadastroSpring.model.Pet;
import com.example.desafioCadastroSpring.repository.PetRepository;
import com.example.desafioCadastroSpring.service.PetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    //    private static final Logger log = LoggerFactory.getLogger(NameValidator.class);
    @Autowired
    private final PetService petService;

    public PetController(PetRepository petRepository, PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public ResponseEntity savePet(@RequestBody @Valid PetDto petDto) {
        petService.savePet(petDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity updatePet(@PathVariable Long id, @RequestBody @Valid PetDto petDto) {
        return ResponseEntity.ok(petService.updatePet(id, petDto));
    }

    @GetMapping
    public ResponseEntity getAllPets(){
        return ResponseEntity.ok(petService.getAllPets());
    }

    @GetMapping("/{id}")
    public ResponseEntity getPetsById(@PathVariable Long id){
        return ResponseEntity.ok(petService.getPetById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePet(@PathVariable Long id){
        petService.deletePet(id);
        return ResponseEntity.noContent().build();
    }

    // Busca Pets por atributos
    @PostMapping("/search")
    public ResponseEntity searchPets(@RequestBody @Valid SearchPetDto dto) {
        List<Pet> all = petService.searchPetByParameter(dto);
        return ResponseEntity.ok(all);
    }
}
