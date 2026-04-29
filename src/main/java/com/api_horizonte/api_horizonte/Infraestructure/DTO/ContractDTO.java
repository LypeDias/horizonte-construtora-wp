package com.api_horizonte.api_horizonte.Infraestructure.DTO;

import com.api_horizonte.api_horizonte.Infraestructure.Entities.ContractStatus;
import com.api_horizonte.api_horizonte.Infraestructure.Entities.RealState;
import com.api_horizonte.api_horizonte.Infraestructure.Entities.UnitsRealState;
import com.api_horizonte.api_horizonte.Infraestructure.Entities.User;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContractDTO(
        LocalDate datePurchase,
        BigDecimal purchaseValue,
        ContractStatus statusContract,
        User userCPF,
        RealState realState,
        UnitsRealState unitsRealState
) {
}
