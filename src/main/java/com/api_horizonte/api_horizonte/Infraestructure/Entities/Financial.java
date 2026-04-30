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

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

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

    // Campos adicionados na Financial:

    @Column(name = "installmentNumber", nullable = false)
    private Integer installmentNumber; // Número da parcela (1, 2, 3...)

    @Column(name = "paidAt")
    private LocalDateTime paidAt; // Data/hora do pagamento efetivo

    @Column(name = "fineAmount")
    private BigDecimal fineAmount; // Multa por atraso

    @Column(name = "interestAmount")
    private BigDecimal interestAmount; // Juros por atraso

    @Column(name = "totalAmount")
    private BigDecimal totalAmount; // amount + multa + juros
}
