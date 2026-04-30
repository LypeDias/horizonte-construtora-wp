package com.api_horizonte.api_horizonte.Infraestructure.DTO;

import com.api_horizonte.api_horizonte.Infraestructure.Entities.ContractStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContractResponse(
        Integer id,
        String contractNumber,
        LocalDate datePurchase,
        BigDecimal purchaseValue,
        BigDecimal installmentValue,
        Integer installmentsTotal,
        ContractStatus statusContract,
        String userName,
        String unitDescription
) {}
