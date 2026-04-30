package com.api_horizonte.api_horizonte.Infraestructure.DTO;

import com.api_horizonte.api_horizonte.Infraestructure.Entities.FinancialStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record FinancialResponse(
        Integer id,
        Integer installmentNumber,
        BigDecimal amount,
        BigDecimal totalAmount,
        LocalDate maturity,
        FinancialStatus status,
        String numberTicket,
        String linkPayment,
        LocalDateTime paidAt
) {
}
