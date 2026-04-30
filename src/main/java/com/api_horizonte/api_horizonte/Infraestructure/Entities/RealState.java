package com.api_horizonte.api_horizonte.Infraestructure.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name="RealStates")

@Entity
public class RealState {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "buildStatus", nullable = false)
    private Double buildStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "RealStateStatus")
    private RealStateStatus realStateStatus;

    @Column(name = "dateStart", nullable = false)
    private LocalDate dateStart;

    @Column(name = "dateDelivery", nullable = false)
    private LocalDate dateDelivery;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;
}
