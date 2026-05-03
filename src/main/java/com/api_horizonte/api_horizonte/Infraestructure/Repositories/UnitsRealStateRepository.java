package com.api_horizonte.api_horizonte.Infraestructure.Repositories;

import com.api_horizonte.api_horizonte.Infraestructure.Entities.RealState;
import com.api_horizonte.api_horizonte.Infraestructure.Entities.UnitsRealState;
import com.api_horizonte.api_horizonte.Infraestructure.Entities.UnitsRealStateStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UnitsRealStateRepository extends JpaRepository<UnitsRealState, Integer> {

    Optional<UnitsRealState> findUnitsRealStateById(int id);

    Optional<UnitsRealState> findUnitsRealStateByStatus(UnitsRealStateStatus status);

    Optional<UnitsRealState> findUnitsRealStateByRealState(RealState realState);

    Optional<UnitsRealState> findUnitsRealStateByFootage(double footage);

    Optional<UnitsRealState> findUnitsRealStateByFloor(int floor);

    List<UnitsRealState> findByRealStateNameContainingIgnoreCase(String name);

    boolean existsByRealStateAndFloorAndNumber(
            RealState realState,
            Integer floor,
            Integer number
    );

    boolean existsByRealStateAndFloorAndNumberAndIdNot(
            RealState realState,
            Integer floor,
            Integer number,
            Integer id
    );

}
