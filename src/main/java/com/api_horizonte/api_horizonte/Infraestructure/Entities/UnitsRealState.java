package com.api_horizonte.api_horizonte.Infraestructure.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name="UnitsRealState")

@Entity
public class UnitsRealState {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "number", nullable = false)
    private Integer number;

    @Column(name = "floor", nullable = false)
    private Integer floor;

    @Column(name = "footage", nullable = false)
    private Double footage;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private UnitsRealStateStatus status;

    @ManyToOne
    @JoinColumn(name = "realStateId", nullable = false)
    private RealState realState;

    @Column(name="price", nullable = false)
    private BigDecimal price;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;
}
