package com.example.desafioCadastroSpring.specification;

import com.example.desafioCadastroSpring.model.Pet;
import org.springframework.data.jpa.domain.Specification;

public class AddressSpecification {

    public static Specification<Pet> streetContains(String street) {
        return ((root, query, builder) ->
                street == null ? null : builder.like(builder.lower(root.join("address").get("street")), "%" + street.toLowerCase() + "%"));
    }

    public static Specification<Pet> numberEquals(Integer number) {
        return ((root, query, builder) ->
                number == null ? null : builder.equal(root.join("address").get("number"), number));

    }

    public static Specification<Pet> cityContains(String city) {
        return ((root, query, builder) ->
                city == null ? null : builder.like(builder.lower(root.join("address").get("city")), "%" + city.toLowerCase() + "%"));

    }
}
