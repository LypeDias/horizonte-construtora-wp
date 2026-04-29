package com.api_horizonte.api_horizonte.Infraestructure.Repositories;

import com.api_horizonte.api_horizonte.Infraestructure.Entities.Contract;
import com.api_horizonte.api_horizonte.Infraestructure.Entities.RealState;
import com.api_horizonte.api_horizonte.Infraestructure.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Integer> {

    Optional<Contract> findContractById(int id);

    Optional<Contract> findContractByUserCPF(User userCPF);

    Optional<Contract> findContractByRealState(RealState realState);

}
