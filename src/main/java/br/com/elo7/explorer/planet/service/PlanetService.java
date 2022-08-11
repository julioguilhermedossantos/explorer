package br.com.elo7.explorer.planet.service;

import br.com.elo7.explorer.planet.dto.PlanetDTO;
import br.com.elo7.explorer.planet.model.Planet;
import br.com.elo7.explorer.planet.repository.PlanetRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanetService {

    private final PlanetRepository planetRepository;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public void createPlanet(PlanetDTO planetDTO) {
        planetRepository.saveAndFlush(objectMapper.convertValue(planetDTO, Planet.class));
    }

    public List<PlanetDTO> listPlanets() {
        List<PlanetDTO> list = new ArrayList<>();
        return planetRepository.findAll()
                .stream()
                .map(planet -> objectMapper.convertValue(planet, PlanetDTO.class))
                .collect(Collectors.toList());
    }

    @SneakyThrows
    public void deletePlanet(Long id) {
        planetRepository.deleteById(id);
    }
}