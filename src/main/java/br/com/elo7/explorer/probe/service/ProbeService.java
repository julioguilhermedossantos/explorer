package br.com.elo7.explorer.probe.service;

import br.com.elo7.explorer.advice.excepion.CollisionExpection;
import br.com.elo7.explorer.advice.excepion.NotAllowedActionException;
import br.com.elo7.explorer.advice.excepion.OrbitalLimitExceededException;
import br.com.elo7.explorer.advice.excepion.UnknownPlanetException;
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
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @SneakyThrows
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

    public void moveProbe(ActionDTO actionDTO, Long probeId){

        var probe = probeRepository.findById(probeId)
                .orElseThrow(() -> new NotFoundException("Sonda não encontrada!"));

        var actions = actionDTO.getAction().toUpperCase().toCharArray();

        for (Character action: actions){
            if(Stream.of(AllowedActions.values()).noneMatch(allowedAction -> allowedAction.getValue().equals(action))){
                throw new NotAllowedActionException("Ação desconhecida!");
            }
            probe.execute(action);
        }
    }
}
