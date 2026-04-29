package com.api_horizonte.api_horizonte.Infraestructure.DTO;

import com.api_horizonte.api_horizonte.Infraestructure.Entities.Contract;
import com.api_horizonte.api_horizonte.Infraestructure.Entities.FinancialStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FinancialDTO(
        BigDecimal amount,
        LocalDate maturity,
        FinancialStatus status,
        String numbertTicket,
        String linkPayment,
        Contract contract
) {
}
