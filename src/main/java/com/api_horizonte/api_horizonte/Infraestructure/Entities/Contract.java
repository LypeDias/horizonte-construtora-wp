package com.api_horizonte.api_horizonte.Infraestructure.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name="Contracts")

@Entity
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "datePurchase", nullable = false)
    private LocalDate datePurchase;

    @Column(name = "purchaseValue", nullable = false)
    private BigDecimal purchaseValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "statusContract", nullable = false)
    private ContractStatus statusContract;

    @ManyToOne
    @JoinColumn(name = "userCPF", nullable = false)
    private User userCPF;

    @ManyToOne
    @JoinColumn(name = "realStateId", nullable = false)
    private RealState realState;

    @ManyToOne
    @JoinColumn(name = "unitsRealStateId", nullable = false)
    private UnitsRealState unitsRealState;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    // campos adicionados na Contract:

    @Column(name = "downPayment")
    private BigDecimal downPayment; // Valor de entrada

    @Column(name = "installmentsTotal", nullable = false)
    private Integer installmentsTotal; // Total de parcelas (ex: 120)

    @Column(name = "installmentValue", nullable = false)
    private BigDecimal installmentValue; // Valor de cada parcela

    @Column(name = "interestRate")
    private BigDecimal interestRate; // Taxa de juros mensal (ex: 0.8%)

    @Column(name = "contractNumber", nullable = false, unique = true)
    private String contractNumber; // Ex: "HRZ-2024-00001"
}
