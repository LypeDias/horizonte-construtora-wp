package com.api_horizonte.api_horizonte.Infraestructure.DTO;

import com.api_horizonte.api_horizonte.Infraestructure.Entities.RealState;
import com.api_horizonte.api_horizonte.Infraestructure.Entities.RealStateStatus;

import java.time.LocalDate;

public record RealStateDTO(
        String name,
        String description,
        String state,
        String city,
        String address,
        double buildStatus,
        RealStateStatus realStateStatus,
        LocalDate dateStart,
        LocalDate dateDelivery ) {
}
