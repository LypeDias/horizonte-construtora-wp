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
@Table(name="Financial")

@Entity
public class Financial {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "value", nullable = false)
    private BigDecimal value;

    @Column(name = "maturity", nullable = false)
    private LocalDate maturity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private FinancialStatus status;

    //numero boleto
    @Column(name = "numberTicket", nullable = false)
    private String numberTicket;

    @Column(name = "linkPayment", nullable = false)
    private  String linkPayment;

    @ManyToOne
    @JoinColumn(name = "contractId", nullable = false)
    private Contract contract;

    @Column(name = "createdAt", nullable = false)
    private  LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;
}
