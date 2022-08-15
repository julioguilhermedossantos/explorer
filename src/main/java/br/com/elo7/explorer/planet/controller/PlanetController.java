package br.com.elo7.explorer.planet.controller;

import br.com.elo7.explorer.planet.dto.PlanetRequestDTO;
import br.com.elo7.explorer.planet.dto.PlanetResponseDTO;
import br.com.elo7.explorer.planet.service.PlanetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/planets")
public class PlanetController {

    private final PlanetService planetService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody PlanetRequestDTO planetRequestDTO) {
        log.debug(
                "[PlanetController] : Criando {} com tamanhos X({}) e Y({})",
                planetRequestDTO.getName(),
                planetRequestDTO.getSurface().getAxisX(),
                planetRequestDTO.getSurface().getAxisY());
        planetService.createPlanet(planetRequestDTO);
    }

    @GetMapping
    public ResponseEntity<List<PlanetResponseDTO>> getAllPlanets() {
        log.debug("[PlanetController] : Listando planetas");
        return ResponseEntity.ok().body(planetService.listPlanets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanetResponseDTO> getPlanet(@PathVariable Long id) {
        log.debug("[PlanetController] : Buscando planeta por id {}", id);
        return ResponseEntity.ok().body(planetService.find(id));
    }

}
