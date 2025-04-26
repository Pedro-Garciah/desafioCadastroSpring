package com.example.desafioCadastroSpring.controller;

import com.example.desafioCadastroSpring.dto.PetDto;
import com.example.desafioCadastroSpring.dto.SearchPetDto;
import com.example.desafioCadastroSpring.model.Pet;
import com.example.desafioCadastroSpring.repository.PetRepository;
import com.example.desafioCadastroSpring.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.util.ClassUtils.isPresent;

@RestController
@RequestMapping("/pets")
@Tag(name = "Desafio Cadastro Pets")
public class PetController {

    //    private static final Logger log = LoggerFactory.getLogger(NameValidator.class);
    @Autowired
    private final PetService petService;

    public PetController(PetRepository petRepository, PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    @Operation(summary = "Registra um novo Pet", description = "Realiza o registro de um novo Pet válido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registra o Pet"),
            @ApiResponse(responseCode = "400", description = "Informações inválidas")
    })
    public ResponseEntity<Pet> savePet(@RequestBody @Valid PetDto petDto) {
        petService.savePet(petDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Atualiza um Pet", description = "Atualiza o registro de um Pet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualiza o Pet"),
            @ApiResponse(responseCode = "400", description = "Informações inválidas")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Pet> updatePet(@PathVariable Long id, @RequestBody @Valid PetDto petDto) {
        return ResponseEntity.ok(petService.updatePet(id, petDto));
    }

    @GetMapping
    @Operation(summary = "Lista todos os Pets", description = "Exibe todos os Pets cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exibe todos os Pets"),
    })
    public ResponseEntity<List<Pet>> getAllPets() {
        return ResponseEntity.ok(petService.getAllPets());
    }

    @Operation(summary = "Busca Pet por Id", description = "Busca um Pet por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exibe todos os Pets"),
            @ApiResponse(responseCode = "404", description = "Id não encontrado"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPetById(@PathVariable Long id) {
        Optional<Pet> petOpt = petService.getPetById(id);
        if (petOpt.isPresent())
            return ResponseEntity.ok(petOpt.get());

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Deleta Pet por Id", description = "Deleta um Pet por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pet Deletado"),
            @ApiResponse(responseCode = "404", description = "Id não encontrado"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<PetDto> deletePet(@PathVariable Long id) {
        petService.deletePet(id);
        return ResponseEntity.noContent().build();
    }

    // Busca Pets por atributos
    @Operation(summary = "Busca Pets por qualquer atributo", description = "Busca Pets por atributos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pets Encontrados"),
            @ApiResponse(responseCode = "400", description = "Informações inválidas"),
    })
    @PostMapping("/search")
    public ResponseEntity<List<Pet>> searchPets(@RequestBody SearchPetDto dto) {
        List<Pet> all = petService.searchPetByParameter(dto);
        return ResponseEntity.ok(all);
    }
}
