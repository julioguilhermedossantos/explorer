package br.com.elo7.explorer.planet.controller;

import br.com.elo7.explorer.planet.dto.PlanetRequestDTO;
import br.com.elo7.explorer.planet.dto.PlanetResponseDTO;
import br.com.elo7.explorer.planet.model.Planet;
import br.com.elo7.explorer.planet.service.PlanetService;
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
@RequestMapping("/planets")
public class PlanetController {

    private final PlanetService planetService;

    private final ObjectMapper objectMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody PlanetRequestDTO planetRequestDTO) {

        log.debug(
                "[PlanetController] : Criando {} com tamanhos X({}) e Y({})",
                planetRequestDTO.getName(),
                planetRequestDTO.getSurface().getAxisX(),
                planetRequestDTO.getSurface().getAxisY()
        );

        planetService.create(objectMapper.convertValue(planetRequestDTO, Planet.class));
    }

    @GetMapping
    public ResponseEntity<List<PlanetResponseDTO>> getAllPlanets() {

        log.debug("[PlanetController] : Listando planetas");

        return ResponseEntity.ok().body(
                planetService.list().stream()
                .map(planet -> objectMapper.convertValue(planet, PlanetResponseDTO.class))
                .collect(Collectors.toList())
        );

    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanetResponseDTO> findById(@PathVariable Long id) {

        log.debug("[PlanetController] : Buscando planeta por id {}", id);

        return ResponseEntity.ok().body(
                objectMapper.convertValue(
                        planetService.findById(id),
                        PlanetResponseDTO.class)
        );
    }

}
