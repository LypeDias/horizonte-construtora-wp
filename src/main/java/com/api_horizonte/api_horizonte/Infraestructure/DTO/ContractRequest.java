package com.api_horizonte.api_horizonte.Infraestructure.DTO;

import com.api_horizonte.api_horizonte.Infraestructure.Entities.ContractStatus;
import com.api_horizonte.api_horizonte.Infraestructure.Entities.RealState;
import com.api_horizonte.api_horizonte.Infraestructure.Entities.UnitsRealState;
import com.api_horizonte.api_horizonte.Infraestructure.Entities.User;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContractRequest(
        String userCPF,
        Integer realStateId,
        Integer unitsRealStateId,
        BigDecimal downPayment,
        Integer installmentsTotal,
        BigDecimal interestRate   // opcional, pode vir do imóvel
) {}
