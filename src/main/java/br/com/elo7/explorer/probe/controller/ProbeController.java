package br.com.elo7.explorer.probe.controller;

import br.com.elo7.explorer.planet.dto.PlanetResponseDTO;
import br.com.elo7.explorer.probe.dto.ActionDTO;
import br.com.elo7.explorer.probe.dto.PositionDTO;
import br.com.elo7.explorer.probe.dto.ProbeRequestDTO;
import br.com.elo7.explorer.probe.dto.ProbeResponseDTO;
import br.com.elo7.explorer.probe.model.Action;
import br.com.elo7.explorer.probe.model.Position;
import br.com.elo7.explorer.probe.model.Probe;
import br.com.elo7.explorer.probe.service.ProbeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/probes")
public class ProbeController {

    private final ProbeService probeService;

    private final ObjectMapper objectMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody ProbeRequestDTO probeRequestDTO) {

        log.debug("[PlanetController] : Criando sonda");

        var planetId = probeRequestDTO.getPlanetId();

        var positon = objectMapper.convertValue(probeRequestDTO.getPosition(), Position.class);

        var probe = Probe.builder()
                .name(probeRequestDTO.getName())
                .pointTo(probeRequestDTO.getPointTo())
                .position(positon)
                .build();

        probeService.create(probe, planetId);

    }

    @GetMapping
    public ResponseEntity<List<ProbeResponseDTO>> list() {

        return ResponseEntity.ok().body(probeService.list().stream()
                .map(probe ->
                        ProbeResponseDTO.builder()
                                .id(probe.getId())
                                .name(probe.getName())
                                .position(objectMapper.convertValue(probe.getPosition(), PositionDTO.class))
                                .pointTo(probe.getPointTo())
                                .planet(objectMapper.convertValue(probe.getPlanet(), PlanetResponseDTO.class))
                                .build())
                .collect(Collectors.toList()));

    }

    @GetMapping("/{id}")
    public ResponseEntity<ProbeResponseDTO> findById(@PathVariable Long id) {

        log.debug("[PlanetController] : Buscando sonda por id {}", id);

        return ResponseEntity.ok().body(objectMapper.convertValue(probeService.find(id), ProbeResponseDTO.class));

    }

    @PatchMapping("/{id}")
    public void move(@Valid @RequestBody ActionDTO actionDTO, @PathVariable Long id) {

        log.debug("[PlanetController] : movendo sonda: instrução {}", actionDTO.getAction());

        actionDTO.validate();

        probeService.move(objectMapper.convertValue(actionDTO, Action.class), id);

    }

}
