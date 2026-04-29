package com.api_horizonte.api_horizonte.Infraestructure.DTO;

import java.time.LocalDate;

public record RealStateDTO(
        String name,
        String description,
        String city,
        String address,
        double buildStatus,
        LocalDate dateStart,
        LocalDate dateDelivery ) {
}
