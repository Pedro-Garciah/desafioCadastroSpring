package com.example.desafioCadastroSpring.model;

import com.example.desafioCadastroSpring.model.Enums.Sex;
import com.example.desafioCadastroSpring.model.Enums.Type;
import jakarta.persistence.*;

@Entity
@Table(name = "tb-pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id")
    private Address address;

    private Double age;

    private Double weight;

    private String breed;

    public Pet() {
    }

    public Pet(String name, Type type, Sex sex, Address address, double age, double weight, String breed) {
        this.name = name;
        this.type = type;
        this.sex = sex;
        this.address = address;
        this.age = age;
        this.weight = weight;
        this.breed = breed;
    }

    public Pet(Long id, String name, Type type, Sex sex, Address address, Double age, Double weight, String breed) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.sex = sex;
        this.address = address;
        this.age = age;
        this.weight = weight;
        this.breed = breed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public double getAge() {
        return age;
    }

    public void setAge(Double age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }
}
