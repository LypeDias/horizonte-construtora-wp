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

    public void created(){
        this.createdAt = LocalDateTime.now();
    }
}
