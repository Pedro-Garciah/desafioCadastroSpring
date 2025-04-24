package com.example.desafioCadastroSpring.service;

import com.example.desafioCadastroSpring.dto.AddressDto;
import com.example.desafioCadastroSpring.dto.PetDto;
import com.example.desafioCadastroSpring.dto.SearchPetDto;
import com.example.desafioCadastroSpring.model.Address;
import com.example.desafioCadastroSpring.model.Enums.Sex;
import com.example.desafioCadastroSpring.model.Enums.Type;
import com.example.desafioCadastroSpring.model.Pet;
import com.example.desafioCadastroSpring.repository.PetRepository;
import com.example.desafioCadastroSpring.specification.PetSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetService petService;

    @Captor
    private ArgumentCaptor<Pet> petArgumentCaptor;

    @Captor
    private ArgumentCaptor<Long> idArgumentCaptor;

    @Nested
    class savePet {

        @Test
        @DisplayName("Should create a Pet with success")
        void shouldCreateAPetWithSuccess() {

            //Arrange
            var address = new Address(
                    "rua doze",
                    21,
                    "cidadelandia");

            var pet = new Pet(
                    Long.parseLong("1"),
                    "pet um",
                    Type.CACHORRO,
                    Sex.MALE,
                    address,
                    6.0,
                    7.0,
                    "Pastor Alemão");

            doReturn(pet).when(petRepository).save(petArgumentCaptor.capture());

            var inputAddress = new AddressDto(
                    "rua doze",
                    21,
                    "cidadelandia");

            var inputPet = new PetDto(
                    "pet um",
                    Type.CACHORRO,
                    Sex.MALE,
                    inputAddress,
                    6.0,
                    7.0,
                    "Pastor Alemão");
            //Act
            var output = petService.savePet(inputPet);

            //Assert
            assertNotNull(output);

            var petCaptured = petArgumentCaptor.getValue();

            assertEquals(inputPet.name(), petCaptured.getName());
            assertEquals(inputPet.type(), petCaptured.getType());
            assertEquals(inputPet.sex(), petCaptured.getSex());
            assertEquals(inputPet.address().toAddress().getCity(), petCaptured.getAddress().getCity());
            assertEquals(inputPet.address().toAddress().getNumber(), petCaptured.getAddress().getNumber());
            assertEquals(inputPet.address().toAddress().getStreet(), petCaptured.getAddress().getStreet());
            assertEquals(inputPet.age(), petCaptured.getAge());
            assertEquals(inputPet.weight(), petCaptured.getWeight());
        }

        @Test
        @DisplayName("Should throws exception when error occurs")
        void shouldThrowExceptionWhenErrorOccurs() {

            doThrow(new RuntimeException()).when(petRepository).save(any());

            var inputAddress = new AddressDto(
                    "rua doze",
                    21,
                    "cidadelandia");

            var inputPet = new PetDto(
                    "pet um",
                    Type.CACHORRO,
                    Sex.MALE,
                    inputAddress,
                    6.0,
                    7.0,
                    "Pastor Alemão");

            //Act & Assert
            assertThrows(RuntimeException.class, () -> petService.savePet(inputPet));
        }
    }

    @Nested
    class getPetById {
        @Test
        @DisplayName("Should get pet by id with success when optional is present")
        void shouldGetPetByIdWithSuccessWhenOptionalIsPresent() {
            // Arrange
            var address = new Address(
                    "rua doze",
                    21,
                    "cidadelandia");

            var pet = new Pet(
                    Long.parseLong("1"),
                    "pet um",
                    Type.CACHORRO,
                    Sex.MALE,
                    address,
                    6.0,
                    7.0,
                    "Pastor Alemão");

            doReturn(Optional.of(pet)).when(petRepository).findById(idArgumentCaptor.capture());

            // Act
            var output = petService.getPetById(pet.getId());

            // Assert
            assertNotNull(output);
            assertEquals(pet.getId(), idArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Should throw exception when pet not found")
        void shouldThrowExceptionWhenPetNotFound() {
            // Arrange
            Long id = 99L;
            doReturn(Optional.empty()).when(petRepository).findById(idArgumentCaptor.capture());

            // Act
            // Assert
            assertThrows(EntityNotFoundException.class, () -> petService.getPetById(id));
            assertEquals(id, idArgumentCaptor.getValue());
        }
    }

    @Nested
    class getAllPets {
        @Test
        void shouldReturnAllPetsWithSuccess() {

            //Arrange

            var address = new Address(
                    "rua doze",
                    21,
                    "cidadelandia");

            var pet = new Pet(
                    Long.parseLong("1"),
                    "pet um",
                    Type.CACHORRO,
                    Sex.MALE,
                    address,
                    6.0,
                    7.0,
                    "Pastor Alemão");

            List<Pet> petList = List.of(pet);
            doReturn(List.of(pet)).when(petRepository).findAll();


            //Act
            var output = petService.getAllPets();
            //Assert
            assertNotNull(output);
            assertEquals(petList.size(), output.size());
            assertEquals(petList.get(0).getName(), output.get(0).getName());
        }

        @Test
        void shouldReturnEmptyListWhenNoPets() {
            //Arrange
            doReturn(List.of()).when(petRepository).findAll();

            //Act
            List<Pet> output = petService.getAllPets();

            //Assert
            assertNotNull(output);
            assertTrue(output.isEmpty());
        }
    }

    @Nested
    class deleteById {

        @Test
        @DisplayName("Should delete Pet with Success when Pet exists")
        void shouldDeletePetWithSuccessWhenPetExists() {

            //Arrange

            var id = 1L;
            var address = new Address(
                    "rua doze",
                    21,
                    "cidadelandia");

            var pet = new Pet(
                    Long.parseLong("1"),
                    "pet um",
                    Type.CACHORRO,
                    Sex.MALE,
                    address,
                    6.0,
                    7.0,
                    "Pastor Alemão");

            doReturn(Optional.of(pet))
                    .when(petRepository)
                    .findById(idArgumentCaptor.capture());

            doNothing()
                    .when(petRepository)
                    .deleteById(idArgumentCaptor.capture());
            //Act

            petService.deletePet(id);

            //Assert
            List<Long> capturedValues = idArgumentCaptor.getAllValues();
            assertEquals(id, capturedValues.get(0));
            assertEquals(id, capturedValues.get(1));

            verify(petRepository, times(1)).findById(capturedValues.get(0));
            verify(petRepository, times(1)).deleteById(capturedValues.get(1));
        }

        @Test
        @DisplayName("Should not delete Pet when Pet not exists")
        void shouldNotDeletePetWhenPetNotExists() {

            //Arrange

            var id = 1L;


            doReturn(Optional.empty())
                    .when(petRepository)
                    .findById(idArgumentCaptor.capture());

            //Act

            petService.deletePet(id);

            //Assert
            assertEquals(id, idArgumentCaptor.getValue());

            verify(petRepository, times(1))
                    .findById(idArgumentCaptor.getValue());

            verify(petRepository, times(0)).deleteById(any());
        }


    }

    @Nested
    class updatePet {
        @Test
        @DisplayName("Should update Pet by Id when Pet exists")
        void shouldUpdatePetWithSuccessWhenPetExists() {
            // Arrange
            var address = new Address(
                    "rua doze",
                    21,
                    "cidadelandia");

            var pet = new Pet(
                    Long.parseLong("1"),
                    "pet um",
                    Type.CACHORRO,
                    Sex.MALE,
                    address,
                    6.0,
                    7.0,
                    "Pastor Alemão");

            doReturn(Optional.of(pet))
                    .when(petRepository)
                    .findById(idArgumentCaptor.capture());

            doReturn(pet)
                    .when(petRepository)
                    .save(petArgumentCaptor.capture());

            var updatedAddressDto = new AddressDto(
                    "rua treze",
                    20,
                    "cidadelindia");

            var updatedPetDto = new PetDto(
                    "pet dois",
                    Type.CACHORRO,
                    Sex.MALE,
                    updatedAddressDto,
                    6.0,
                    7.0,
                    "Pastor Belga");

            // Act
            var output = petService.updatePet(pet.getId(), updatedPetDto);

            // Assert
            assertNotNull(output);
            assertEquals(pet.getId(), idArgumentCaptor.getValue());

            var petCaptured = petArgumentCaptor.getValue();

            assertEquals(updatedPetDto.name(), petCaptured.getName());
            assertEquals(updatedPetDto.type(), petCaptured.getType());
            assertEquals(updatedPetDto.sex(), petCaptured.getSex());
            assertEquals(updatedPetDto.address().city(), petCaptured.getAddress().getCity());
            assertEquals(updatedPetDto.address().number(), petCaptured.getAddress().getNumber());
            assertEquals(updatedPetDto.address().street(), petCaptured.getAddress().getStreet());
            assertEquals(updatedPetDto.age(), petCaptured.getAge());
            assertEquals(updatedPetDto.weight(), petCaptured.getWeight());
            assertEquals(updatedPetDto.breed(), petCaptured.getBreed());

            verify(petRepository, times(1))
                    .findById(idArgumentCaptor.capture());

            verify(petRepository, times(1))
                    .save(pet);
        }

        @Test
        @DisplayName("Should not update Pet by Id when Pet not exists")
        void shouldNotUpdatePetWithSuccessWhenPetNotExists() {
            // Arrange
            var id = 1L;
            doReturn(Optional.empty())
                    .when(petRepository)
                    .findById(idArgumentCaptor.capture());

            var updatedAddressDto = new AddressDto(
                    "rua treze",
                    20,
                    "cidadelindia");

            var updatedPetDto = new PetDto(
                    "pet dois",
                    Type.CACHORRO,
                    Sex.MALE,
                    updatedAddressDto,
                    6.0,
                    7.0,
                    "Pastor Belga");

            // Assert
            assertThrows(EntityNotFoundException.class, () -> petService.updatePet(id, updatedPetDto));
            assertEquals(id, idArgumentCaptor.getValue());
            verify(petRepository, times(1))
                    .findById(idArgumentCaptor.capture());

            verify(petRepository, times(0))
                    .save(any());
        }
    }

    @Nested
    class searchPetByParameter {
        @Test
        @DisplayName("Should return pets by parameter with success")
        void shouldReturnPetsByParameterWithSuccess() {
            // Arrange
            Pet pet = new Pet("pet 1",
                    Type.CACHORRO,
                    null,
                    null,
                    0,
                    0,
                    null);

            List<Pet> petList = List.of(pet);

            SearchPetDto searchPetDto = new SearchPetDto(
                    "pet 1",
                    Type.CACHORRO,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            doReturn(petList).when(petRepository).findAll(any(Specification.class));

            // Act
            List<Pet> output = petService.searchPetByParameter(searchPetDto);

            // Assert
            assertNotNull(output);
            assertEquals(1, output.size());
            assertEquals(pet.getName(), output.getFirst().getName());

            verify(petRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        @DisplayName("Should return empty list when no pets matches parameter")
        void shouldReturnEmptyListWhenNoPetsMatchesParameters() {
            SearchPetDto searchPetDto = new SearchPetDto(
                    "pet 1",
                    Type.CACHORRO,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            doReturn(List.of()).when(petRepository).findAll(any(Specification.class));

            var output = petService.searchPetByParameter(searchPetDto);

            assertNotNull(output);
            assertTrue(output.isEmpty());
            verify(petRepository, times(1)).findAll(any(Specification.class));
        }
    }
}