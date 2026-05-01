package com.api_horizonte.api_horizonte.Business;

import com.api_horizonte.api_horizonte.Infraestructure.DTO.ContractRequest;
import com.api_horizonte.api_horizonte.Infraestructure.DTO.ContractResponse;
import com.api_horizonte.api_horizonte.Infraestructure.DTO.ContractUpdateRequest;
import com.api_horizonte.api_horizonte.Infraestructure.Entities.*;
import com.api_horizonte.api_horizonte.Infraestructure.Repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;
    private final FinancialRepository financialRepository;
    private final UserRepository userRepository;
    private final RealStateRepository realStateRepository;
    private final UnitsRealStateRepository unitsRealStateRepository;

    public List<ContractResponse> findContractByUserCPF(String cpf){
        User user = userRepository.findUserByCpf(cpf).orElseThrow(
                () -> new RuntimeException("Usuario não encontrado")
        );

        List<Contract> contract = contractRepository.findContractByUser(user);

        if(contract.isEmpty()){
            throw new RuntimeException("Não existe contratos no nome desse usuário");
        }

        return contract.stream().map(this::toResponse).toList();
    }

    public List<ContractResponse> findContractByRealStateByName(String name){
        RealState realState = realStateRepository.findRealStateByName(name).orElseThrow(
                () -> new RuntimeException("Empreendimento não encontrado")
        );

        List<Contract> contract = contractRepository.findContractByRealState(realState);

        if(contract.isEmpty()){
            throw new RuntimeException("Nenhum contrato encontrado para esse empreendimento");
        }

        return contract.stream().map(this::toResponse).toList();
    }

    @Transactional
    public ContractResponse createContract(ContractRequest request) {

        // 1. Buscar entidades
        User user = userRepository.findUserByCpf(request.userCPF())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        UnitsRealState unit = unitsRealStateRepository.findUnitsRealStateById(request.unitsRealStateId())
                .orElseThrow(() -> new RuntimeException("Unidade não encontrada"));

        // 2. Verificar se a unidade está disponível
        if (unit.getStatus() != UnitsRealStateStatus.DISPONIVEL
                && unit.getStatus() != UnitsRealStateStatus.RESERVADO) {
            throw new RuntimeException("Unidade não está disponível para venda");
        }

        // 3. Calcular valor da parcela
        BigDecimal valorFinanciado = unit.getPrice().subtract(request.downPayment());
        BigDecimal installmentValue = calculateInstallment(
                valorFinanciado,
                request.installmentsTotal(),
                request.interestRate()
        );

        // 4. Criar contrato — usando apenas os campos que existem na sua Entity
        Contract contract = Contract.builder()
                .contractNumber(generateContractNumber())
                .datePurchase(LocalDate.now())
                .purchaseValue(unit.getPrice())
                .downPayment(request.downPayment())
                .installmentsTotal(request.installmentsTotal())
                .installmentValue(installmentValue)
                .interestRate(request.interestRate())
                .statusContract(ContractStatus.ASSINATURA_PENDENTE)
                .userCPF(user)
                .realState(unit.getRealState())
                .unitsRealState(unit)
                .createdAt(LocalDateTime.now())
                .build();

        contractRepository.save(contract);

        // 5. Marcar unidade como vendida
        unit.setStatus(UnitsRealStateStatus.VENDIDO);
        unitsRealStateRepository.save(unit);

        // 6. Gerar todas as parcelas automaticamente
        generateInstallments(contract);

        return toResponse(contract);
    }

    @Transactional
    public ContractResponse updateContract(Integer contractId, ContractUpdateRequest request) {

        // 1. Buscar contrato existente
        Contract contract = contractRepository.findContractById(contractId)
                .orElseThrow(() -> new RuntimeException("Contrato não encontrado"));

        // 2. Impedir atualização de contratos já encerrados
        if (contract.getStatusContract() == ContractStatus.QUITADO
                || contract.getStatusContract() == ContractStatus.CANCELADO) {
            throw new RuntimeException(
                    "Não é possível alterar um contrato com status: "
                            + contract.getStatusContract().name()
            );
        }

        // 3. Atualizar status se informado
        if (request.statusContract() != null) {

            validateStatusTransition(contract.getStatusContract(), request.statusContract());
            contract.setStatusContract(request.statusContract());

            // Se cancelado: liberar unidade + cancelar parcelas pendentes
            if (request.statusContract() == ContractStatus.CANCELADO) {
                UnitsRealState unit = contract.getUnitsRealState();
                unit.setStatus(UnitsRealStateStatus.DISPONIVEL);
                unitsRealStateRepository.save(unit);

                cancelPendingInstallments(contract);
            }
        }

        contract.setUpdatedAt(LocalDateTime.now());
        contractRepository.save(contract);

        return toResponse(contract);
    }

    // -------------------------------------------------------------------------
    // Métodos privados
    // -------------------------------------------------------------------------

    private ContractResponse toResponse(Contract contract) {
        return new ContractResponse(
                contract.getId(),
                contract.getContractNumber(),
                contract.getDatePurchase(),
                contract.getPurchaseValue(),
                contract.getDownPayment(),
                contract.getInstallmentValue(),
                contract.getInstallmentsTotal(),
                contract.getInterestRate(),
                contract.getStatusContract(),
                // User
                contract.getUserCPF().getName(),
                contract.getUserCPF().getCpf(),
                // RealState
                contract.getRealState().getName(),
                // UnitsRealState
                contract.getUnitsRealState().getId(),
                contract.getCreatedAt(),
                contract.getUpdatedAt()
        );
    }

    // Fórmula Price com fallback para divisão simples sem juros
    private BigDecimal calculateInstallment(
            BigDecimal principal,
            int months,
            BigDecimal monthlyRate) {

        if (monthlyRate == null || monthlyRate.compareTo(BigDecimal.ZERO) == 0) {
            return principal.divide(BigDecimal.valueOf(months), 2, RoundingMode.HALF_UP);
        }

        // P * [i(1+i)^n] / [(1+i)^n - 1]
        double p = principal.doubleValue();
        double i = monthlyRate.doubleValue() / 100;
        int n = months;

        double pmt = p * (i * Math.pow(1 + i, n)) / (Math.pow(1 + i, n) - 1);

        return BigDecimal.valueOf(pmt).setScale(2, RoundingMode.HALF_UP);
    }

    private void generateInstallments(Contract contract) {
        List<Financial> installments = new ArrayList<>();

        for (int i = 1; i <= contract.getInstallmentsTotal(); i++) {
            Financial financial = Financial.builder()
                    .installmentNumber(i)
                    .amount(contract.getInstallmentValue())
                    .totalAmount(contract.getInstallmentValue())
                    .maturity(contract.getDatePurchase().plusMonths(i))
                    .status(FinancialStatus.PENDENTE)
                    .numberTicket(generateTicketNumber(contract, i))
                    .linkPayment(generatePaymentLink(contract, i))
                    .contract(contract)
                    .createdAt(LocalDateTime.now())
                    .build();

            installments.add(financial);
        }

        financialRepository.saveAll(installments);
    }

    // Garante que só transições válidas são permitidas
    private void validateStatusTransition(ContractStatus current, ContractStatus next) {
        boolean valid = switch (current) {
            case ASSINATURA_PENDENTE -> next == ContractStatus.ATIVO
                    || next == ContractStatus.CANCELADO;
            case ATIVO -> next == ContractStatus.QUITADO
                    || next == ContractStatus.CANCELADO;
            default                  -> false;
        };

        if (!valid) {
            throw new RuntimeException(
                    "Transição de status inválida: " + current + " → " + next
            );
        }
    }

    private void cancelPendingInstallments(Contract contract) {
        List<Financial> pending = financialRepository
                .findByContractIdAndStatus(contract.getId(), FinancialStatus.PENDENTE);

        pending.forEach(f -> {
            f.setStatus(FinancialStatus.CANCELADO);
            f.setUpdatedAt(LocalDateTime.now());
        });

        financialRepository.saveAll(pending);
    }

    private String generateContractNumber() {
        int year = LocalDate.now().getYear();
        long count = contractRepository.count() + 1;
        return String.format("HRZ-%d-%05d", year, count);
    }

    private String generateTicketNumber(Contract contract, int installment) {
        return String.format("1234.56789 %05d.%06d %d.%06d %d %d%010d",
                contract.getId(), installment,
                contract.getId(), installment,
                1,
                LocalDate.now().getYear(),
                (long) (Math.random() * 9999999999L)
        );
    }

    private String generatePaymentLink(Contract contract, int installment) {
        return String.format("https://pagamento.horizonte.com.br/boleto/%s/%d",
                contract.getContractNumber(), installment);
    }
}