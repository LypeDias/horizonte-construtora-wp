package com.api_horizonte.api_horizonte.Infraestructure.DTO;

import com.api_horizonte.api_horizonte.Infraestructure.Entities.UnitsRealStateStatus;

import java.math.BigDecimal;

public record UnitsRealStateResponse(
        Integer number,
        Integer floor,
        Double footage,
        UnitsRealStateStatus status,
        String realStateName,
        BigDecimal price) {
}
