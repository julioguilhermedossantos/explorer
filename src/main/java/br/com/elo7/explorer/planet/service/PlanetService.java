package br.com.elo7.explorer.planet.service;

import br.com.elo7.explorer.advice.excepion.UnknownPlanetException;
import br.com.elo7.explorer.planet.model.Planet;
import br.com.elo7.explorer.planet.repository.PlanetRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanetService {

    private final PlanetRepository planetRepository;

    @SneakyThrows
    public void create(Planet planet) {

        log.debug("[PLANET SERVICE] : Criando planeta");

        planetRepository.saveAndFlush(planet);

    }

    public List<Planet> list() {

        log.debug("[PLANET SERVICE] : Listando planetas");

        return planetRepository.findAll();

    }

    public Planet findById(Long id) {

        log.debug("[PLANET SERVICE] : Buscando planet por id {}", id);

        return planetRepository.findById(id)
                .orElseThrow(() -> new UnknownPlanetException("Planeta não encontrado"));

    }

}
