package com.api_horizonte.api_horizonte.Infraestructure.Repositories;

import com.api_horizonte.api_horizonte.Infraestructure.Entities.RealState;
import com.api_horizonte.api_horizonte.Infraestructure.Entities.RealStateStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RealStateRepository extends JpaRepository<RealState, Integer> {

    Optional<RealState> findRealStateById(int id);

    Optional<RealState> findRealStateByName(String name);

    List<RealState> findRealStateByCity(String city);

    List<RealState> findRealStateByState(String state);

    List<RealState> findRealStateByRealStateStatus(RealStateStatus realStateStatus);

    List<RealState> findRealStateByDateDelivery(LocalDate date);

    boolean existsByNameAndCityAndState(
            String name,
            String city,
            String state
    );

    boolean existsByNameAndCityAndStateAndIdNot(
            String name,
            String city,
            String state,
            Integer id
    );

}
