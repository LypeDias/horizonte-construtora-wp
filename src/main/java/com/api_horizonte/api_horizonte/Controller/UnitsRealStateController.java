package com.api_horizonte.api_horizonte.Controllers;

import com.api_horizonte.api_horizonte.Infraestructure.DTO.UnitsRealStateDTO;
import com.api_horizonte.api_horizonte.Infraestructure.Entities.RealState;
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
    public ResponseEntity<List<UnitsRealStateDTO>> findAllUnitsRealState() {
        List<UnitsRealStateDTO> units = unitsRealStateService.findAllUnitsRealState();
        return ResponseEntity.ok(units);
    }

    // GET /units/{id} → busca unidade por ID
    @GetMapping("/{id}")
    public ResponseEntity<UnitsRealStateDTO> findUnitsRealStateById(@PathVariable int id) {
        UnitsRealStateDTO unit = unitsRealStateService.findUnitsRealStateById(id);
        return ResponseEntity.ok(unit);
    }

    // GET /units/status/{status} → busca unidade por status (DISPONIVEL, RESERVADO, VENDIDO)
    @GetMapping("/status/{status}")
    public ResponseEntity<UnitsRealStateDTO> findUnitsRealStateByStatus(
            @PathVariable UnitsRealStateStatus status) {
        UnitsRealStateDTO unit = unitsRealStateService.findUnitsRealStateByStatus(status);
        return ResponseEntity.ok(unit);
    }

    // GET /units/floor/{floor} → busca unidade por andar
    @GetMapping("/floor/{floor}")
    public ResponseEntity<UnitsRealStateDTO> findUnitsRealStateByFloor(@PathVariable int floor) {
        UnitsRealStateDTO unit = unitsRealStateService.findUnitsRealStateByFloor(floor);
        return ResponseEntity.ok(unit);
    }

    // GET /units/footage/{footage} → busca unidade por metragem
    @GetMapping("/footage/{footage}")
    public ResponseEntity<UnitsRealStateDTO> findUnitsRealStateByFootage(@PathVariable double footage) {
        UnitsRealStateDTO unit = unitsRealStateService.findUnitsRealStateByFootage(footage);
        return ResponseEntity.ok(unit);
    }

    // GET /units/realstate → busca unidades por empreendimento (RealState no body)
    @GetMapping("/realstate")
    public ResponseEntity<UnitsRealStateDTO> findUnitsRealStateByRealState(
            @RequestBody RealState realState) {
        UnitsRealStateDTO unit = unitsRealStateService.findUnitsRealStateByRealState(realState);
        return ResponseEntity.ok(unit);
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
    public ResponseEntity<UnitsRealStateDTO> updateUnitsRealStateById(
            @PathVariable int id,
            @RequestBody UnitsRealStateDTO unitsRealStateDTO) {
        UnitsRealStateDTO updatedUnit = unitsRealStateService.updateUnitsRealStateById(id, unitsRealStateDTO);
        return ResponseEntity.ok(updatedUnit);
    }
}