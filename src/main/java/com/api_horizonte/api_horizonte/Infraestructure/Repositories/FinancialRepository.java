package com.api_horizonte.api_horizonte.Infraestructure.Repositories;

import com.api_horizonte.api_horizonte.Infraestructure.Entities.Contract;
import com.api_horizonte.api_horizonte.Infraestructure.Entities.Financial;
import com.api_horizonte.api_horizonte.Infraestructure.Entities.FinancialStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface FinancialRepository extends JpaRepository<Financial, Integer> {

    Optional<Financial> findFinancialById(int id);

    Optional<Financial> findFinancialByMaturity(LocalDate maturity);

    Optional<Financial> findFinancialByStatus(FinancialStatus status);

    Optional<Financial> findFinancialByContract(Contract contract);

}
