package com.api_horizonte.api_horizonte.Infraestructure.Repositories;

import com.api_horizonte.api_horizonte.Infraestructure.Entities.RealState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface RealStateRepository extends JpaRepository<RealState, Integer> {

    Optional<RealState> findRealStateById(int id);

    Optional<RealState> findRealStateByName(String name);

    Optional<RealState> findRealStateByCity(String city);

    Optional<RealState> findRealStateByState(String state);

    Optional<RealState> findRealStateByDateDelivery(LocalDate date);

}
