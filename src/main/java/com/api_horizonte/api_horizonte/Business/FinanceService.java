package com.api_horizonte.api_horizonte.Business;

import com.api_horizonte.api_horizonte.Infraestructure.DTO.FinanceResponse;
import com.api_horizonte.api_horizonte.Infraestructure.Entities.*;
import com.api_horizonte.api_horizonte.Infraestructure.Repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FinanceService {

    private final FinanceRepository financialRepository;
    private final ContractRepository contractRepository;
    private final UserRepository userRepository;

    // -------------------------------------------------------------------------
    // Finds
    // -------------------------------------------------------------------------

    public FinanceResponse findFinancialById(Integer id) {
        Financial financial = financialRepository.findFinancialById(id)
                .orElseThrow(() -> new RuntimeException("Parcela não encontrada"));

        return toResponse(financial);
    }

    public FinanceResponse findFinancialByMaturity(LocalDate maturity) {
        Financial financial = financialRepository.findFinancialByMaturity(maturity)
                .orElseThrow(() -> new RuntimeException("Nenhuma parcela encontrada para esse vencimento"));

        return toResponse(financial);
    }

    public FinanceResponse findFinancialByStatus(FinancialStatus status) {
        Financial financial = financialRepository.findFinancialByStatus(status)
                .orElseThrow(() -> new RuntimeException("Nenhuma parcela encontrada com esse status"));

        return toResponse(financial);
    }

    public List<FinanceResponse> findInstallmentsByContract(Integer contractId) {
        Contract contract = contractRepository.findContractById(contractId)
                .orElseThrow(() -> new RuntimeException("Contrato não encontrado"));

        List<Financial> financials = financialRepository.findFinancialByContract(contract);

        if (financials.isEmpty()) {
            throw new RuntimeException("Nenhuma parcela encontrada para esse contrato");
        }

        return financials.stream()
                .map(this::toResponse)
                .toList();
    }

    public List<FinanceResponse> findMyInstallments(String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return financialRepository.findByContract_User_Cpf(user.getCpf())
                .stream()
                .map(financial -> new FinanceResponse(
                        financial.getId(),
                        financial.getInstallmentNumber(),
                        financial.getAmount(),
                        financial.getTotalAmount(),
                        financial.getMaturity(),
                        financial.getStatus(),
                        financial.getNumberTicket(),
                        financial.getLinkPayment(),
                        financial.getPaidAt()
                ))
                .toList();
    }

    public List<FinanceResponse> findOverdueByContract(Integer contractId) {
        contractRepository.findContractById(contractId)
                .orElseThrow(() -> new RuntimeException("Contrato não encontrado"));

        List<Financial> overdue = financialRepository
                .findByContractIdAndStatus(contractId, FinancialStatus.ATRASADO);

        if (overdue.isEmpty()) {
            throw new RuntimeException("Nenhuma parcela em atraso para esse contrato");
        }

        return overdue.stream().map(this::toResponse).toList();
    }

    public List<FinanceResponse> findPendingByContract(Integer contractId) {
        contractRepository.findContractById(contractId)
                .orElseThrow(() -> new RuntimeException("Contrato não encontrado"));

        List<Financial> pending = financialRepository
                .findByContractIdAndStatus(contractId, FinancialStatus.PENDENTE);

        if (pending.isEmpty()) {
            throw new RuntimeException("Nenhuma parcela pendente para esse contrato");
        }

        return pending.stream().map(this::toResponse).toList();
    }

    // -------------------------------------------------------------------------
    // Regras de negócio
    // -------------------------------------------------------------------------

    // Registrar pagamento de uma parcela
    @Transactional
    public FinanceResponse registerPayment(Integer financialId) {
        Financial financial = financialRepository.findFinancialById(financialId)
                .orElseThrow(() -> new RuntimeException("Parcela não encontrada"));

        // Impede pagamento de parcela já paga ou cancelada
        if (financial.getStatus() == FinancialStatus.PAGO) {
            throw new RuntimeException("Essa parcela já foi paga");
        }
        if (financial.getStatus() == FinancialStatus.CANCELADO) {
            throw new RuntimeException("Essa parcela foi cancelada e não pode ser paga");
        }

        // Se estiver atrasada, recalcula multa e juros antes de pagar
        if (financial.getStatus() == FinancialStatus.ATRASADO) {
            applyFinesIfOverdue(financial);
        }

        financial.setStatus(FinancialStatus.PAGO);
        financial.setPaidAt(LocalDateTime.now());
        financial.setUpdatedAt(LocalDateTime.now());

        financialRepository.save(financial);

        // Verifica se todas as parcelas do contrato foram pagas
        checkContractCompletion(financial.getContract());

        return toResponse(financial);
    }

    // Atualiza parcelas vencidas para ATRASADO — roda todo dia às 8h
    @Transactional
    @Scheduled(cron = "0 0 8 * * *")
    public void updateOverdueInstallments() {

        List<Financial> nowOverdue = financialRepository
                .findByStatusAndMaturityBefore(FinancialStatus.PENDENTE, LocalDate.now());

        nowOverdue.forEach(f -> {
            f.setStatus(FinancialStatus.ATRASADO);
            applyFinesIfOverdue(f);
            f.setUpdatedAt(LocalDateTime.now());
        });

        financialRepository.saveAll(nowOverdue);
    }

    // -------------------------------------------------------------------------
    // Métodos privados
    // -------------------------------------------------------------------------

    private void applyFinesIfOverdue(Financial financial) {
        long daysLate = ChronoUnit.DAYS.between(financial.getMaturity(), LocalDate.now());

        if (daysLate <= 0) return;

        // Multa fixa: 2% sobre o valor original
        BigDecimal fine = financial.getAmount()
                .multiply(BigDecimal.valueOf(0.02))
                .setScale(2, RoundingMode.HALF_UP);

        // Juros: 0,033% ao dia (equivale a ~1% ao mês)
        BigDecimal interest = financial.getAmount()
                .multiply(BigDecimal.valueOf(0.00033))
                .multiply(BigDecimal.valueOf(daysLate))
                .setScale(2, RoundingMode.HALF_UP);

        financial.setFineAmount(fine);
        financial.setInterestAmount(interest);
        financial.setTotalAmount(
                financial.getAmount().add(fine).add(interest)
                        .setScale(2, RoundingMode.HALF_UP)
        );
    }

    private void checkContractCompletion(Contract contract) {
        // Conta parcelas que ainda não foram pagas (PENDENTE ou ATRASADO)
        long unpaidPending = financialRepository
                .findByContractIdAndStatus(contract.getId(), FinancialStatus.PENDENTE)
                .size();

        long unpaidOverdue = financialRepository
                .findByContractIdAndStatus(contract.getId(), FinancialStatus.ATRASADO)
                .size();

        // Se não houver nenhuma parcela em aberto, quita o contrato
        if (unpaidPending == 0 && unpaidOverdue == 0) {
            contract.setStatusContract(ContractStatus.QUITADO);
            contract.setUpdatedAt(LocalDateTime.now());
            contractRepository.save(contract);
        }
    }

    private FinanceResponse toResponse(Financial financial) {
        return new FinanceResponse(
                financial.getId(),
                financial.getInstallmentNumber(),
                financial.getAmount(),
                financial.getTotalAmount(),
                financial.getMaturity(),
                financial.getStatus(),
                financial.getNumberTicket(),
                financial.getLinkPayment(),
                financial.getPaidAt()
        );
    }
}