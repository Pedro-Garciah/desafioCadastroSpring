package com.example.desafioCadastroSpring.dto;

import com.example.desafioCadastroSpring.model.Address;

public record AddressDto(
        String street,
        Integer number,
        String city) {

    public Address toAddress() {
        return new Address(
                street,
                number,
                city);
    }

}
