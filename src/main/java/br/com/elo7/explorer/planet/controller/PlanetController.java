package br.com.elo7.explorer.planet.controller;

import br.com.elo7.explorer.planet.dto.PlanetDTO;
import br.com.elo7.explorer.planet.service.PlanetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/planets")
public class PlanetController {

    private final PlanetService planetService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody PlanetDTO planetDTO){
        planetService.createPlanet(planetDTO);
    }

    @GetMapping
    public ResponseEntity<List<PlanetDTO>> getAllPlanets(){
       return ResponseEntity.ok().body(planetService.listPlanets());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        planetService.deletePlanet(id);
    }
}
