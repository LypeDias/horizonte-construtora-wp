package com.api_horizonte.api_horizonte.Infraestructure.DTO;

public record UserRequestRefresh(
        String cpf,

        String name,

        String email,

        String oldPassword,

        String newPassword) {
}
