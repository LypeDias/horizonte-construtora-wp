package com.api_horizonte.api_horizonte.Business;

import com.api_horizonte.api_horizonte.Infraestructure.DTO.RealStateDTO;
import com.api_horizonte.api_horizonte.Infraestructure.Entities.RealState;
import com.api_horizonte.api_horizonte.Infraestructure.Entities.RealStateStatus;
import com.api_horizonte.api_horizonte.Infraestructure.Repositories.RealStateRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RealStateService {

    private final RealStateRepository realStateRepository;

    public RealStateService(RealStateRepository realStateRepository) {
        this.realStateRepository = realStateRepository;
    }

    public List<RealStateDTO> findAllRealStates(){
        List<RealState> realStates = realStateRepository.findAll();
        if(realStates.isEmpty()){
            throw new RuntimeException("Nenhum empreendimento encontrado");
        }
        return realStates.stream().map(this::toDTO).toList();
    }

    public RealStateDTO findRealStateById(int id) {
        RealState realState = realStateRepository.findRealStateById(id)
                .orElseThrow(() -> new RuntimeException("Empreendimento não encontrado"));
        return toDTO(realState);
    }

    public RealStateDTO findRealStateByName(String name) {
        RealState realState = realStateRepository.findRealStateByName(name)
                .orElseThrow(() -> new RuntimeException("Empreendimento não encontrado"));
        return toDTO(realState);
    }

    public List<RealStateDTO> findRealStateByCity(String city) {
        List<RealState> realStates = realStateRepository.findRealStateByCity(city);
        if (realStates.isEmpty()) {
            throw new RuntimeException("Nenhum empreendimento encontrado para a cidade: " + city);
        }
        return realStates.stream().map(this::toDTO).toList();
    }

    public List<RealStateDTO> findRealStateByState(String state) {
        List<RealState> realStates = realStateRepository.findRealStateByState(state);
        if (realStates.isEmpty()) {
            throw new RuntimeException("Nenhum empreendimento encontrado para o estado: " + state);
        }
        return realStates.stream().map(this::toDTO).toList();
    }

    public List<RealStateDTO> findRealStateByDateDelivery(LocalDate dateDelivery) {
        List<RealState> realStates = realStateRepository.findRealStateByDateDelivery(dateDelivery);
        if (realStates.isEmpty()) {
            throw new RuntimeException("Nenhum empreendimento encontrado para a data: " + dateDelivery);
        }
        return realStates.stream().map(this::toDTO).toList();
    }

    public List<RealStateDTO> findRealStateByRealStateStatus(RealStateStatus realStateStatus) {
        List<RealState> realStates = realStateRepository.findRealStateByRealStateStatus(realStateStatus);
        if (realStates.isEmpty()) {
            throw new RuntimeException("Nenhum empreendimento encontrado com o status: " + realStateStatus);
        }
        return realStates.stream().map(this::toDTO).toList();
    }

    // -------------------------------------------------------------------------
    // Criar / Atualizar
    // -------------------------------------------------------------------------

    public RealStateDTO createRealState(RealStateDTO realStateDTO) {
        boolean exists = realStateRepository.existsByNameAndCityAndState(
                realStateDTO.name(),
                realStateDTO.city(),
                realStateDTO.state()
        );

        if (exists) {
            throw new RuntimeException("Empreendimento já existe");
        }

        RealState realState = RealState.builder()
                .name(realStateDTO.name())
                .description(realStateDTO.description())
                .city(realStateDTO.city())
                .state(realStateDTO.state())
                .address(realStateDTO.address())
                .buildStatus(realStateDTO.buildStatus())
                .realStateStatus(realStateDTO.realStateStatus())
                .dateStart(realStateDTO.dateStart())
                .dateDelivery(realStateDTO.dateDelivery())
                .createdAt(LocalDateTime.now())
                .build();

        realStateRepository.save(realState);

        return toDTO(realState);
    }

    public RealStateDTO updateRealStateById(int id, RealStateDTO realStateDTO) {
        RealState realState = realStateRepository.findRealStateById(id)
                .orElseThrow(() -> new RuntimeException("Empreendimento não encontrado"));

        boolean exists = realStateRepository.existsByNameAndCityAndStateAndIdNot(
                realStateDTO.name(),
                realStateDTO.city(),
                realStateDTO.state(),
                id
        );

        if (exists) {
            throw new RuntimeException("Já existe outro empreendimento com esse nome nessa cidade/estado");
        }

        realState.setName(realStateDTO.name());
        realState.setDescription(realStateDTO.description());
        realState.setState(realStateDTO.state());
        realState.setCity(realStateDTO.city());
        realState.setAddress(realStateDTO.address());
        realState.setBuildStatus(realStateDTO.buildStatus());
        realState.setRealStateStatus(realStateDTO.realStateStatus());
        realState.setDateStart(realStateDTO.dateStart());
        realState.setDateDelivery(realStateDTO.dateDelivery());
        realState.setUpdatedAt(LocalDateTime.now());

        realStateRepository.save(realState);

        return toDTO(realState);
    }

    private RealStateDTO toDTO(RealState realState) {
        return new RealStateDTO(
                realState.getName(),
                realState.getDescription(),
                realState.getState(),
                realState.getCity(),
                realState.getAddress(),
                realState.getBuildStatus(),
                realState.getRealStateStatus(),
                realState.getDateStart(),
                realState.getDateDelivery()
        );
    }
}