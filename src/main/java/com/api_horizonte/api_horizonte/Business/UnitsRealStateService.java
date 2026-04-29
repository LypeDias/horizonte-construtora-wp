package com.api_horizonte.api_horizonte.Business;

import com.api_horizonte.api_horizonte.Infraestructure.DTO.UnitsRealStateDTO;
import com.api_horizonte.api_horizonte.Infraestructure.Entities.RealState;
import com.api_horizonte.api_horizonte.Infraestructure.Entities.UnitsRealState;
import com.api_horizonte.api_horizonte.Infraestructure.Entities.UnitsRealStateStatus;
import com.api_horizonte.api_horizonte.Infraestructure.Repositories.UnitsRealStateRepository;
import jakarta.persistence.metamodel.IdentifiableType;
import org.springframework.format.annotation.DurationFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UnitsRealStateService {

    final UnitsRealStateRepository unitsRealStateRepository;

    public UnitsRealStateService(UnitsRealStateRepository unitsRealStateRepository) {
        this.unitsRealStateRepository = unitsRealStateRepository;
    }

    public List<UnitsRealStateDTO> findAllUnitsRealState(){
        return unitsRealStateRepository.findAll()
                .stream()
                .map(UnitsRealState -> new UnitsRealStateDTO(
                        UnitsRealState.getNumber(),
                        UnitsRealState.getFloor(),
                        UnitsRealState.getFootage(),
                        UnitsRealState.getStatus(),
                        UnitsRealState.getRealState()
                ))
                .toList();
    }

    public UnitsRealStateDTO findUnitsRealStateById(int id){
        UnitsRealState unitsRealState = unitsRealStateRepository.findUnitsRealStateById(id).orElseThrow(
                () -> new RuntimeException("Id da unidade não encontrado")
        );

        return new UnitsRealStateDTO(
                unitsRealState.getNumber(),
                unitsRealState.getFloor(),
                unitsRealState.getFootage(),
                unitsRealState.getStatus(),
                unitsRealState.getRealState()
        );
    }

    public UnitsRealStateDTO findUnitsRealStateByStatus(UnitsRealStateStatus unitsRealStateStatus){
        UnitsRealState unitsRealState = unitsRealStateRepository.findUnitsRealStateByStatus(unitsRealStateStatus).orElseThrow(
                () -> new RuntimeException("Status de unidade é inválido")
        );

        return new UnitsRealStateDTO(
                unitsRealState.getNumber(),
                unitsRealState.getFloor(),
                unitsRealState.getFootage(),
                unitsRealState.getStatus(),
                unitsRealState.getRealState()
        );
    }

    public UnitsRealStateDTO findUnitsRealStateByRealState(RealState realState){
        UnitsRealState unitsRealState = unitsRealStateRepository.findUnitsRealStateByRealState(realState).orElseThrow(
                () -> new RuntimeException("Empreendimento não encontrado")
        );

        return new UnitsRealStateDTO(
                unitsRealState.getNumber(),
                unitsRealState.getFloor(),
                unitsRealState.getFootage(),
                unitsRealState.getStatus(),
                unitsRealState.getRealState()
        );
    }

    public UnitsRealStateDTO findUnitsRealStateByFootage(double footage){
        UnitsRealState unitsRealState = unitsRealStateRepository.findUnitsRealStateByFootage(footage).orElseThrow(
                () -> new RuntimeException("Metragem não encontrada")
        );

        return new UnitsRealStateDTO(
                unitsRealState.getNumber(),
                unitsRealState.getFloor(),
                unitsRealState.getFootage(),
                unitsRealState.getStatus(),
                unitsRealState.getRealState()
        );
    }

    public UnitsRealStateDTO findUnitsRealStateByFloor(int floor){
        UnitsRealState unitsRealState = unitsRealStateRepository.findUnitsRealStateByFloor(floor).orElseThrow(
                () -> new RuntimeException("Andar não encontrada")
        );

        return new UnitsRealStateDTO(
                unitsRealState.getNumber(),
                unitsRealState.getFloor(),
                unitsRealState.getFootage(),
                unitsRealState.getStatus(),
                unitsRealState.getRealState()
        );
    }

    public UnitsRealStateDTO createUnitsRealState(UnitsRealStateDTO unitsRealStateDTO){
        boolean exists = unitsRealStateRepository.existsByRealStateAndFloorAndNumber(
                unitsRealStateDTO.realState(),
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
                .realState(unitsRealStateDTO.realState())
                .createdAt(LocalDateTime.now())
                .build();

        unitsRealStateRepository.save(unit);

        return unitsRealStateDTO;
    }

    public UnitsRealStateDTO updateUnitsRealStateById (int id, UnitsRealStateDTO dtoRefresh){
        UnitsRealState unitsRealState = unitsRealStateRepository.findUnitsRealStateById(id).orElseThrow(
                () -> new RuntimeException("Unidade não encontrada")
        );

        boolean exists = unitsRealStateRepository.existsByRealStateAndFloorAndNumberAndIdNot(
                dtoRefresh.realState(),
                dtoRefresh.floor(),
                dtoRefresh.number(),
                id
        );

        if(exists){
            throw new RuntimeException("Não foi possível atualizar porque já existe outra unidade com essas informações");
        }

        unitsRealState.setFloor(dtoRefresh.floor() != null ? dtoRefresh.floor() : unitsRealState.getFloor());
        unitsRealState.setNumber(dtoRefresh.number() != null ? dtoRefresh.number() : unitsRealState.getNumber());
        unitsRealState.setRealState(dtoRefresh.realState() != null ? dtoRefresh.realState() : unitsRealState.getRealState());
        unitsRealState.setFootage(dtoRefresh.footage() != null ? dtoRefresh.footage() : unitsRealState.getFootage());

        if(dtoRefresh.status() != null) {
            if(unitsRealState.getStatus() == UnitsRealStateStatus.VENDIDO){
                throw new RuntimeException("Não é possível alterar uma unidade vendida");
            }

            unitsRealState.setStatus(dtoRefresh.status());
        }

        unitsRealState.setUpdatedAt(LocalDateTime.now());

        unitsRealStateRepository.save(unitsRealState);

        return new UnitsRealStateDTO(
                unitsRealState.getNumber(),
                unitsRealState.getFloor(),
                unitsRealState.getFootage(),
                unitsRealState.getStatus(),
                unitsRealState.getRealState()
        );
    }
}
