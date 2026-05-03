package com.api_horizonte.api_horizonte.Controller;

import com.api_horizonte.api_horizonte.Infraestructure.DTO.UnitsRealStateDTO;
import com.api_horizonte.api_horizonte.Infraestructure.DTO.UnitsRealStateResponse;
import com.api_horizonte.api_horizonte.Infraestructure.Entities.UnitsRealStateStatus;
import com.api_horizonte.api_horizonte.Business.UnitsRealStateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/units")
public class UnitsRealStateController {

    final UnitsRealStateService unitsRealStateService;

    public UnitsRealStateController(UnitsRealStateService unitsRealStateService) {
        this.unitsRealStateService = unitsRealStateService;
    }

    // GET /units → lista todas as unidades
    @GetMapping
    public ResponseEntity<List<UnitsRealStateResponse>> findAllUnitsRealState() {
        List<UnitsRealStateResponse> units = unitsRealStateService.findAllUnitsRealState();
        return ResponseEntity.ok(units);
    }

    // GET /units/{id} → busca unidade por ID
    @GetMapping("/{id}")
    public ResponseEntity<UnitsRealStateResponse> findUnitsRealStateById(@PathVariable int id) {
        UnitsRealStateResponse unit = unitsRealStateService.findUnitsRealStateById(id);
        return ResponseEntity.ok(unit);
    }

    // GET /units/status/{status} → busca unidade por status (DISPONIVEL, RESERVADO, VENDIDO)
    @GetMapping("/status/{status}")
    public ResponseEntity<UnitsRealStateResponse> findUnitsRealStateByStatus(
            @PathVariable UnitsRealStateStatus status) {
        UnitsRealStateResponse unit = unitsRealStateService.findUnitsRealStateByStatus(status);
        return ResponseEntity.ok(unit);
    }

    // GET /units/floor/{floor} → busca unidade por andar
    @GetMapping("/floor/{floor}")
    public ResponseEntity<UnitsRealStateResponse> findUnitsRealStateByFloor(@PathVariable int floor) {
        UnitsRealStateResponse unit = unitsRealStateService.findUnitsRealStateByFloor(floor);
        return ResponseEntity.ok(unit);
    }

    // GET /units/footage/{footage} → busca unidade por metragem
    @GetMapping("/footage/{footage}")
    public ResponseEntity<UnitsRealStateResponse> findUnitsRealStateByFootage(@PathVariable double footage) {
        UnitsRealStateResponse unit = unitsRealStateService.findUnitsRealStateByFootage(footage);
        return ResponseEntity.ok(unit);
    }

    // GET /units/realstate → busca unidades por empreendimento (RealState no body)
    @GetMapping("/realstate")
    public ResponseEntity<List<UnitsRealStateResponse>> findUnitsRealStateByRealState(
            @RequestParam String name) {

        List<UnitsRealStateResponse> units = unitsRealStateService.findByRealStateName(name);
        return ResponseEntity.ok(units);
    }

    // POST /units → cria nova unidade
    @PostMapping
    public ResponseEntity<UnitsRealStateDTO> createUnitsRealState(
            @RequestBody UnitsRealStateDTO unitsRealStateDTO) {
        UnitsRealStateDTO createdUnit = unitsRealStateService.createUnitsRealState(unitsRealStateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUnit);
    }

    // PUT /units/{id} → atualiza unidade por ID
    @PutMapping("/{id}")
    public ResponseEntity<UnitsRealStateResponse> updateUnitsRealStateById(
            @PathVariable int id,
            @RequestBody UnitsRealStateDTO unitsRealStateDTO) {
        UnitsRealStateResponse updatedUnit = unitsRealStateService.updateUnitsRealStateById(id, unitsRealStateDTO);
        return ResponseEntity.ok(updatedUnit);
    }
}