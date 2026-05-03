package com.api_horizonte.api_horizonte.Controllers;

import com.api_horizonte.api_horizonte.Business.FinanceService;
import com.api_horizonte.api_horizonte.Infraestructure.DTO.FinanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/financial")
@RequiredArgsConstructor
public class FinanceController {

    private final FinanceService financialService;

    // GET /financial/{id}
    @GetMapping("/{id}")
    public ResponseEntity<FinanceResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(financialService.findFinancialById(id));
    }

    // GET /financial/maturity/{date}  ex: /financial/maturity/2026-06-01
    @GetMapping("/maturity/{date}")
    public ResponseEntity<FinanceResponse> findByMaturity(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(financialService.findFinancialByMaturity(date));
    }

    // GET /financial/status/{status}  ex: /financial/status/ATRASADO
    @GetMapping("/status/{status}")
    public ResponseEntity<FinanceResponse> findByStatus(
            @PathVariable String status) {
        return ResponseEntity.ok(
                financialService.findFinancialByStatus(
                        com.api_horizonte.api_horizonte.Infraestructure.Entities.FinancialStatus.valueOf(status.toUpperCase())
                )
        );
    }

    // GET /financial/contract/{contractId}
    @GetMapping("/contract/{contractId}")
    public ResponseEntity<List<FinanceResponse>> findByContract(@PathVariable Integer contractId) {
        return ResponseEntity.ok(financialService.findInstallmentsByContract(contractId));
    }

    // GET /financial/contract/{contractId}/overdue
    @GetMapping("/contract/{contractId}/overdue")
    public ResponseEntity<List<FinanceResponse>> findOverdueByContract(@PathVariable Integer contractId) {
        return ResponseEntity.ok(financialService.findOverdueByContract(contractId));
    }

    // GET /financial/contract/{contractId}/pending
    @GetMapping("/contract/{contractId}/pending")
    public ResponseEntity<List<FinanceResponse>> findPendingByContract(@PathVariable Integer contractId) {
        return ResponseEntity.ok(financialService.findPendingByContract(contractId));
    }

    @GetMapping("/me")
    public ResponseEntity<List<FinanceResponse>> findMyInstallments(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(financialService.findMyInstallments(email));
    }

    // PATCH /financial/{id}/pay
    @PatchMapping("/{id}/pay")
    public ResponseEntity<FinanceResponse> registerPayment(@PathVariable Integer id) {
        return ResponseEntity.ok(financialService.registerPayment(id));
    }
}