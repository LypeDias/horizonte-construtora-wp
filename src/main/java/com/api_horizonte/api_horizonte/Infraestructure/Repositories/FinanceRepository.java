package com.api_horizonte.api_horizonte.Infraestructure.Repositories;

import com.api_horizonte.api_horizonte.Infraestructure.Entities.Contract;
import com.api_horizonte.api_horizonte.Infraestructure.Entities.Financial;
import com.api_horizonte.api_horizonte.Infraestructure.Entities.FinancialStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FinanceRepository extends JpaRepository<Financial, Integer> {

    Optional<Financial> findFinancialById(Integer id);

    Optional<Financial> findFinancialByMaturity(LocalDate maturity);

    Optional<Financial> findFinancialByStatus(FinancialStatus status);

    List<Financial> findFinancialByContract(Contract contract);

    List<Financial> findByContractIdAndStatus(
            Integer id,
            FinancialStatus status
    );

    List<Financial> findByStatusAndMaturityBefore(FinancialStatus status, LocalDate date);

    List<Financial> findByContract_User_Cpf(String cpf);

}
