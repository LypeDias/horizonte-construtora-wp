package com.api_horizonte.api_horizonte.Controller;

import com.api_horizonte.api_horizonte.Business.ContractService;
import com.api_horizonte.api_horizonte.Infraestructure.DTO.ContractRequest;
import com.api_horizonte.api_horizonte.Infraestructure.DTO.ContractResponse;
import com.api_horizonte.api_horizonte.Infraestructure.DTO.ContractUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    // POST /contracts
    @PostMapping
    public ResponseEntity<ContractResponse> createContract(@RequestBody ContractRequest request) {
        ContractResponse response = contractService.createContract(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ContractResponse>> findAllContracts(){
        List<ContractResponse> response = contractService.findAllContracts();
        return ResponseEntity.ok(response);
    }

    // GET /contracts/user/{cpf}
    @GetMapping("/user/{cpf}")
    public ResponseEntity<List<ContractResponse>> findContractByUserCPF(@PathVariable String cpf) {
        List<ContractResponse> response = contractService.findContractByUserCPF(cpf);
        return ResponseEntity.ok(response);
    }

    // GET /contracts/realstate/{name}
    @GetMapping("/realstate/{name}")
    public ResponseEntity<List<ContractResponse>> findContractByRealStateName(@PathVariable String name) {
        List<ContractResponse> response = contractService.findContractByRealStateByName(name);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<List<ContractResponse>> findMyContracts(Authentication authentication) {
        String email = authentication.getName(); // pega o email do token
        List<ContractResponse> response = contractService.findContractByEmail(email);
        return ResponseEntity.ok(response);
    }

    // PATCH /contracts/{id}
    @PatchMapping("/{id}")
    public ResponseEntity<ContractResponse> updateContract(
            @PathVariable Integer id,
            @RequestBody ContractUpdateRequest request) {
        ContractResponse response = contractService.updateContract(id, request);
        return ResponseEntity.ok(response);
    }
}