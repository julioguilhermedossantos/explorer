package br.com.elo7.explorer.planet.controller;

import br.com.elo7.explorer.planet.dto.PlanetDTO;
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
    public void create(@Valid @RequestBody PlanetDTO planetDTO){
        log.info(
                "[CONTROLLER] : Criando {} com tamanhos X({}) e Y({}) apontando para {}",
                planetDTO.getName(),
                planetDTO.getSurface().getAxleX(),
                planetDTO.getSurface().getAxleY(),
                planetDTO.getPointTo().value);
        planetService.createPlanet(planetDTO);
    }

    @GetMapping
    public ResponseEntity<List<PlanetDTO>> getAllPlanets(){
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
