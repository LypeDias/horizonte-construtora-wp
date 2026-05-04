package com.api_horizonte.api_horizonte.Controllers;

import com.api_horizonte.api_horizonte.Business.RealStateService;
import com.api_horizonte.api_horizonte.Infraestructure.DTO.RealStateDTO;
import com.api_horizonte.api_horizonte.Infraestructure.Entities.RealStateStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/real-states")
public class RealStateController {

    private final RealStateService realStateService;

    public RealStateController(RealStateService realStateService) {
        this.realStateService = realStateService;
    }

    // -------------------------------------------------------------------------
    // GET
    // -------------------------------------------------------------------------

    @GetMapping("/all")
    public ResponseEntity<List<RealStateDTO>> findAllRealStates(){
        return ResponseEntity.ok(realStateService.findAllRealStates());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RealStateDTO> findById(@PathVariable int id) {
        return ResponseEntity.ok(realStateService.findRealStateById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<RealStateDTO> findByName(@PathVariable String name) {
        return ResponseEntity.ok(realStateService.findRealStateByName(name));
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<RealStateDTO>> findByCity(@PathVariable String city) {
        return ResponseEntity.ok(realStateService.findRealStateByCity(city));
    }

    @GetMapping("/state/{state}")
    public ResponseEntity<List<RealStateDTO>> findByState(@PathVariable String state) {
        return ResponseEntity.ok(realStateService.findRealStateByState(state));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<RealStateDTO>> findByStatus(@PathVariable RealStateStatus status) {
        return ResponseEntity.ok(realStateService.findRealStateByRealStateStatus(status));
    }

    @GetMapping("/delivery-date")
    public ResponseEntity<List<RealStateDTO>> findByDateDelivery(
            @RequestParam("date") LocalDate dateDelivery) {
        return ResponseEntity.ok(realStateService.findRealStateByDateDelivery(dateDelivery));
    }

    // -------------------------------------------------------------------------
    // POST
    // -------------------------------------------------------------------------

    @PostMapping
    public ResponseEntity<RealStateDTO> create(@RequestBody RealStateDTO realStateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(realStateService.createRealState(realStateDTO));
    }

    // -------------------------------------------------------------------------
    // PUT
    // -------------------------------------------------------------------------

    @PutMapping("/{id}")
    public ResponseEntity<RealStateDTO> update(
            @PathVariable int id,
            @RequestBody RealStateDTO realStateDTO) {
        return ResponseEntity.ok(realStateService.updateRealStateById(id, realStateDTO));
    }
}