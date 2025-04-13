package com.example.desafioCadastroSpring.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_address")
public class Address {

    @Id
    private long addressId;
    
    private String rua;
    private int numero;
    private String cidade;
}
