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
    public void create(@Valid @RequestBody PlanetRequestDTO planetRequestDTO){
        log.info(
                "[CONTROLLER] : Criando {} com tamanhos X({}) e Y({})",
                planetRequestDTO.getName(),
                planetRequestDTO.getSurface().getAxleX(),
                planetRequestDTO.getSurface().getAxleY());
        planetService.createPlanet(planetRequestDTO);
    }

    @GetMapping
    public ResponseEntity<List<PlanetResponseDTO>> getAllPlanets(){
        log.info("[CONTROLLER]");
       return ResponseEntity.ok().body(planetService.listPlanets());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        log.info("[CONTROLLER] deletando planeta com id: {}", id);
        planetService.deletePlanet(id);
    }
}
