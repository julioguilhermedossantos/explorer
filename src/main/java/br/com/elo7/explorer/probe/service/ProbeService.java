package br.com.elo7.explorer.probe.service;

import br.com.elo7.explorer.planet.repository.PlanetRepository;
import br.com.elo7.explorer.probe.dto.ProbeDTO;
import br.com.elo7.explorer.probe.model.Probe;
import br.com.elo7.explorer.probe.repository.ProbeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProbeService {

    private final ProbeRepository probeRepository;
    private final PlanetRepository planetRepository;
    private final ObjectMapper objectMapper;

    public List<ProbeDTO> getAll() {
        return probeRepository.findAll()
                .stream()
                .map(probe -> objectMapper.convertValue(probe, ProbeDTO.class))
                .collect(Collectors.toList());
    }

    @SneakyThrows
    public void create(ProbeDTO probeDTO) {
        probeRepository.saveAndFlush(objectMapper.convertValue(probeDTO, Probe.class));
    }
}
