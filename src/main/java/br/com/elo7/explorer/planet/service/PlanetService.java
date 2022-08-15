package br.com.elo7.explorer.planet.service;

import br.com.elo7.explorer.advice.excepion.UnknownPlanetException;
import br.com.elo7.explorer.planet.dto.PlanetRequestDTO;
import br.com.elo7.explorer.planet.dto.PlanetResponseDTO;
import br.com.elo7.explorer.planet.model.Planet;
import br.com.elo7.explorer.planet.repository.PlanetRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanetService {

    private final PlanetRepository planetRepository;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public void createPlanet(PlanetRequestDTO planetRequestDTO) {

        log.debug("[PLANET SERVICE] : Criando planeta");

        planetRepository.saveAndFlush(objectMapper.convertValue(planetRequestDTO, Planet.class));

    }

    public List<PlanetResponseDTO> listPlanets() {

        log.debug("[PLANET SERVICE] : Listando planetas");

        return planetRepository.findAll()
                .stream()
                .map(planet -> objectMapper.convertValue(planet, PlanetResponseDTO.class))
                .collect(Collectors.toList());

    }

    public PlanetResponseDTO find(Long id) {

        log.debug("[PLANET SERVICE] : Buscando planet por id {}", id);

        var planet = planetRepository.findById(id)
                .orElseThrow(() -> new UnknownPlanetException("Planeta não encontrado"));

        return objectMapper.convertValue(planet, PlanetResponseDTO.class);

    }

}
