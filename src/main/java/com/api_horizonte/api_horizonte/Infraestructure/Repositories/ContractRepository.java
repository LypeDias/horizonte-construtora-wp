package com.api_horizonte.api_horizonte.Infraestructure.Repositories;

import com.api_horizonte.api_horizonte.Infraestructure.Entities.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Integer> {

    Optional<Contract> findContractById(int id);

    List<Contract> findContractByUser(User user);

    List<Contract> findContractByRealState(RealState realState);

    List<Contract> findContractByUser_Cpf(String cpf);
}
