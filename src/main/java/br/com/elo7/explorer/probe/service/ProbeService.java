package br.com.elo7.explorer.probe.service;

import br.com.elo7.explorer.advice.excepion.*;
import br.com.elo7.explorer.planet.dto.PlanetResponseDTO;
import br.com.elo7.explorer.planet.repository.PlanetRepository;
import br.com.elo7.explorer.probe.dto.ActionDTO;
import br.com.elo7.explorer.probe.dto.PositionDTO;
import br.com.elo7.explorer.probe.dto.ProbeRequestDTO;
import br.com.elo7.explorer.probe.dto.ProbeResponseDTO;
import br.com.elo7.explorer.probe.enums.AllowedActions;
import br.com.elo7.explorer.probe.model.Position;
import br.com.elo7.explorer.probe.model.Probe;
import br.com.elo7.explorer.probe.repository.ProbeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProbeService {

    private final ProbeRepository probeRepository;
    private final PlanetRepository planetRepository;
    private final ObjectMapper objectMapper;

    public List<ProbeResponseDTO> getAll() {
        log.info("[PROBE SERVICE] Listando sondas");
        return probeRepository.findAll()
                .stream()
                .map(probe ->
                        ProbeResponseDTO.builder()
                                .id(probe.getId())
                                .name(probe.getName())
                                .position(objectMapper.convertValue(probe.getPosition(), PositionDTO.class))
                                .pointTo(probe.getPointingTo())
                                .planet(objectMapper.convertValue(probe.getCurrentExlporingPlanet(), PlanetResponseDTO.class))
                                .build())
                .collect(Collectors.toList());
    }

    public void create(ProbeRequestDTO probeRequestDTO) {
        log.info(String.format("[PROBE SERVICE] Criando sonda %s", probeRequestDTO.getName()));
        var targetPlanet = planetRepository.findById(probeRequestDTO.getPlanetId())
                .orElseThrow(() -> new UnknownPlanetException("Planeta não registrado!"));

        var requiredLandingPosition = objectMapper
                .convertValue(probeRequestDTO.getPosition(), Position.class);

        if (targetPlanet.isRequiredPositionExceedingOrbitalLimit(requiredLandingPosition))
            throw new OrbitalLimitExceededException("Fora do limite orbital!");

        if (targetPlanet.hasProbeLandedAt(requiredLandingPosition))
            throw new CollisionExpection("Existe uma sonda pousada nesta posição!");

        var probe = Probe.builder()
                .name(probeRequestDTO.getName())
                .pointingTo(probeRequestDTO.getPointTo())
                .currentExlporingPlanet(targetPlanet)
                .position(objectMapper.convertValue(probeRequestDTO.getPosition(), Position.class))
                .build();

        targetPlanet.getExploringProbes().add(probe);

        probeRepository.saveAndFlush(probe);
    }

    public void moveProbe(ActionDTO actionDTO, Long probeId) {

        var probe = probeRepository.findById(probeId)
                .orElseThrow(() -> new ProbeNotFoundException("Sonda não encontrada!"));

        var actions = actionDTO.getAction().toUpperCase().toCharArray();

        for (char action : actions) {

            if (this.isNotAllowedAction(action)) {
                throw new NotAllowedActionException("Ação desconhecida!");
            }
            probe.execute(action);
            log.info("pointTo: {}, position x({}) y({})", probe.getPointingTo(), probe.getPosition().getCoordinateX(), probe.getPosition().getCoordinateY());
        }
        log.info("[final] pointTo: {}, position x({}) y({})", probe.getPointingTo(), probe.getPosition().getCoordinateX(), probe.getPosition().getCoordinateY());
    }

    private boolean isNotAllowedAction(char action) {
        return Arrays.stream(AllowedActions.values())
                .noneMatch(allowedAction -> allowedAction.getValue() == action);
    }

    public ProbeResponseDTO find(Long id) {
        var probe = probeRepository.findById(id)
                .orElseThrow(() -> new ProbeNotFoundException("Sonda não encontrada!"));
        return objectMapper.convertValue(probe, ProbeResponseDTO.class);
    }
}
