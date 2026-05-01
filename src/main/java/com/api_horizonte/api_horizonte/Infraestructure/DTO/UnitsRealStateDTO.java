package com.api_horizonte.api_horizonte.Infraestructure.DTO;

import com.api_horizonte.api_horizonte.Infraestructure.Entities.RealState;
import com.api_horizonte.api_horizonte.Infraestructure.Entities.UnitsRealStateStatus;

import java.math.BigDecimal;

public record UnitsRealStateDTO(
        Integer number,
        Integer floor,
        Double footage,
        UnitsRealStateStatus status,
        RealState realState,
        BigDecimal price
) {
}
