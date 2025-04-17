package com.example.desafioCadastroSpring.specification;

import com.example.desafioCadastroSpring.dto.AddressDto;
import com.example.desafioCadastroSpring.model.Enums.Sex;
import com.example.desafioCadastroSpring.model.Enums.Type;
import com.example.desafioCadastroSpring.model.Pet;
import org.springframework.data.jpa.domain.Specification;

public class PetSpecification {

    public static Specification<Pet> nameContains(String name) {
        return ((root, query, builder) ->
                name == null ? null : builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
    }

    public static Specification<Pet> hasType(Type type) {
        return (root, query, builder) ->
                type == null ? null : builder.equal(root.get("type"), type);
    }

    public static Specification<Pet> hasSex(Sex sex) {
        return (root, query, builder) ->
                sex == null ? null : builder.equal(root.get("sex"), sex);
    }

    public static Specification<Pet> addressContains(AddressDto address) {
        return address == null ? null : Specification.where(AddressSpecification.streetContains(address.street()))
                .and(AddressSpecification.numberEquals(address.number()))
                .and(AddressSpecification.cityContains(address.city()));
    }

    public static Specification<Pet> ageEquals(Double age) {
        return (root, query, builder) ->
                age == null ? null : builder.equal(root.get("age"), age);
    }

    public static Specification<Pet> weightEquals(Double weight) {
        return (root, query, builder) ->
                weight == null ? null : builder.equal(root.get("weight"), weight);
    }

    public static Specification<Pet> breedContains(String breed) {
        return (root, query, builder) ->
                breed == null ? null : builder.like(builder.lower(root.get("breed")), "%" + breed + "%");
    }
}
