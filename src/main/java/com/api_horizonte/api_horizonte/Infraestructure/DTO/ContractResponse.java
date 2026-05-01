package com.api_horizonte.api_horizonte.Infraestructure.DTO;

import com.api_horizonte.api_horizonte.Infraestructure.Entities.ContractStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ContractResponse(
        Integer id,
        String contractNumber,
        LocalDate datePurchase,
        BigDecimal purchaseValue,
        BigDecimal downPayment,
        BigDecimal installmentValue,
        Integer installmentsTotal,
        BigDecimal interestRate,
        ContractStatus statusContract,
        String userName,
        String userCpf,
        String realStateName,
        Integer unitIdentifier,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}