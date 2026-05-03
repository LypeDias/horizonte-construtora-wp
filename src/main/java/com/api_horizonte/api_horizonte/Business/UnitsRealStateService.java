package com.api_horizonte.api_horizonte.Business;

import com.api_horizonte.api_horizonte.Infraestructure.DTO.UnitsRealStateDTO;
import com.api_horizonte.api_horizonte.Infraestructure.DTO.UnitsRealStateResponse;
import com.api_horizonte.api_horizonte.Infraestructure.Entities.RealState;
import com.api_horizonte.api_horizonte.Infraestructure.Entities.UnitsRealState;
import com.api_horizonte.api_horizonte.Infraestructure.Entities.UnitsRealStateStatus;
import com.api_horizonte.api_horizonte.Infraestructure.Repositories.RealStateRepository;
import com.api_horizonte.api_horizonte.Infraestructure.Repositories.UnitsRealStateRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UnitsRealStateService {

    final UnitsRealStateRepository unitsRealStateRepository;
    final RealStateRepository realStateRepository;

    public UnitsRealStateService(UnitsRealStateRepository unitsRealStateRepository, RealStateRepository realStateRepository) {
        this.unitsRealStateRepository = unitsRealStateRepository;
        this.realStateRepository = realStateRepository;
    }

    public List<UnitsRealStateResponse> findAllUnitsRealState(){
        return unitsRealStateRepository.findAll()
                .stream()
                .map(UnitsRealState -> new UnitsRealStateResponse(
                        UnitsRealState.getNumber(),
                        UnitsRealState.getFloor(),
                        UnitsRealState.getFootage(),
                        UnitsRealState.getStatus(),
                        UnitsRealState.getRealState().getName(),
                        UnitsRealState.getPrice()
                ))
                .toList();
    }

    public UnitsRealStateResponse findUnitsRealStateById(int id){
        UnitsRealState unitsRealState = unitsRealStateRepository.findUnitsRealStateById(id).orElseThrow(
                () -> new RuntimeException("Id da unidade não encontrado")
        );

        return toDTO(unitsRealState);
    }

    public UnitsRealStateResponse findUnitsRealStateByStatus(UnitsRealStateStatus unitsRealStateStatus){
        UnitsRealState unitsRealState = unitsRealStateRepository.findUnitsRealStateByStatus(unitsRealStateStatus).orElseThrow(
                () -> new RuntimeException("Status de unidade é inválido")
        );

        return toDTO(unitsRealState);
    }

    public List<UnitsRealStateResponse> findByRealStateName(String name) {
        return unitsRealStateRepository.findByRealStateNameContainingIgnoreCase(name)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public UnitsRealStateResponse findUnitsRealStateByFootage(double footage){
        UnitsRealState unitsRealState = unitsRealStateRepository.findUnitsRealStateByFootage(footage).orElseThrow(
                () -> new RuntimeException("Metragem não encontrada")
        );

        return toDTO(unitsRealState);
    }

    public UnitsRealStateResponse findUnitsRealStateByFloor(int floor){
        UnitsRealState unitsRealState = unitsRealStateRepository.findUnitsRealStateByFloor(floor).orElseThrow(
                () -> new RuntimeException("Andar não encontrada")
        );

        return toDTO(unitsRealState);
    }

    public UnitsRealStateDTO createUnitsRealState(UnitsRealStateDTO unitsRealStateDTO){
        RealState realState = realStateRepository.findRealStateById(unitsRealStateDTO.realStateId()).orElseThrow(
                () -> new RuntimeException("Empreendimento não existe")
        );

        boolean exists = unitsRealStateRepository.existsByRealStateAndFloorAndNumber(
                realState,
                unitsRealStateDTO.floor(),
                unitsRealStateDTO.number()
        );

        if(exists){
            throw new RuntimeException("Unidade já existe no banco de dados");
        }

        UnitsRealState unit = UnitsRealState.builder()
                .number(unitsRealStateDTO.number())
                .floor(unitsRealStateDTO.floor())
                .footage(unitsRealStateDTO.footage())
                .status(unitsRealStateDTO.status())
                .realState(realState)
                .price(unitsRealStateDTO.price())
                .createdAt(LocalDateTime.now())
                .build();

        unitsRealStateRepository.save(unit);

        return unitsRealStateDTO;
    }

    public UnitsRealStateResponse updateUnitsRealStateById(int id, UnitsRealStateDTO dtoRefresh) {

        UnitsRealState unitsRealState = unitsRealStateRepository.findUnitsRealStateById(id)
                .orElseThrow(() -> new RuntimeException("Unidade não encontrada"));

        // 🔥 Só busca se vier ID
        RealState realState = unitsRealState.getRealState();

        if (dtoRefresh.realStateId() != null) {
            realState = realStateRepository.findRealStateById(dtoRefresh.realStateId())
                    .orElseThrow(() -> new RuntimeException("Empreendimento não existe"));
        }

        boolean exists = unitsRealStateRepository.existsByRealStateAndFloorAndNumberAndIdNot(
                realState,
                dtoRefresh.floor() != null ? dtoRefresh.floor() : unitsRealState.getFloor(),
                dtoRefresh.number() != null ? dtoRefresh.number() : unitsRealState.getNumber(),
                id
        );

        if (exists) {
            throw new RuntimeException("Não foi possível atualizar porque já existe outra unidade com essas informações");
        }

        // 🔹 Atualizações
        if (dtoRefresh.floor() != null)
            unitsRealState.setFloor(dtoRefresh.floor());

        if (dtoRefresh.number() != null)
            unitsRealState.setNumber(dtoRefresh.number());

        if (dtoRefresh.footage() != null)
            unitsRealState.setFootage(dtoRefresh.footage());

        if (dtoRefresh.price() != null)
            unitsRealState.setPrice(dtoRefresh.price());

        if (dtoRefresh.realStateId() != null)
            unitsRealState.setRealState(realState);

        if (dtoRefresh.status() != null) {
            if (unitsRealState.getStatus() == UnitsRealStateStatus.VENDIDO) {
                throw new RuntimeException("Não é possível alterar uma unidade vendida");
            }
            unitsRealState.setStatus(dtoRefresh.status());
        }

        unitsRealState.setUpdatedAt(LocalDateTime.now());

        unitsRealStateRepository.save(unitsRealState);

        return toDTO(unitsRealState);
    }

    private UnitsRealStateResponse toDTO(UnitsRealState unitsRealState) {
        return new UnitsRealStateResponse(
            unitsRealState.getNumber(),
            unitsRealState.getFloor(),
            unitsRealState.getFootage(),
            unitsRealState.getStatus(),
            unitsRealState.getRealState().getName(),
            unitsRealState.getPrice()
        );
    }
}
