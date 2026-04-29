package com.api_horizonte.api_horizonte.Infraestructure.DTO;

import org.hibernate.annotations.processing.Pattern;

public record UserRequest (

        String cpf,

        String name,

        String email,

        String password) {
}
