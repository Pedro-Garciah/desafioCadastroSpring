package com.example.desafioCadastroSpring.repository;

import com.example.desafioCadastroSpring.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
}
