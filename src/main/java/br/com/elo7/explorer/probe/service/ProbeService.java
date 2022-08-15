package br.com.elo7.explorer.probe.service;

import br.com.elo7.explorer.advice.excepion.ProbeNotFoundException;
import br.com.elo7.explorer.advice.excepion.UnknownPlanetException;
import br.com.elo7.explorer.planet.repository.PlanetRepository;
import br.com.elo7.explorer.probe.dto.ProbeResponseDTO;
import br.com.elo7.explorer.probe.model.Action;
import br.com.elo7.explorer.probe.model.Probe;
import br.com.elo7.explorer.probe.repository.ProbeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProbeService {

    private final ProbeRepository probeRepository;
    private final PlanetRepository planetRepository;


    public List<Probe> list() {

        log.debug("[PROBE SERVICE] : Listando sondas");

        return probeRepository.findAll();

    }

    public void create(Probe probe, Long planetId) {

        log.debug("[PROBE SERVICE] : Criando sonda {}", probe.getName());

        var planet = planetRepository.findById(planetId)
                .orElseThrow(() -> new UnknownPlanetException("Planeta não registrado!"));

        planet.addProbe(probe);

        probeRepository.saveAndFlush(probe);
    }

    public void move(Action action, Long probeId) {

        log.debug("[PROBE SERVICE] : Movendo sonda de acordo com as instruções {}", action.getAction());

        var probe = probeRepository.findById(probeId)
                .orElseThrow(() -> new ProbeNotFoundException("Sonda não encontrada!"));

        for (char act : action.split()) {

            probe.execute(act);

        }

        probe.getPlanet().validate(probe.getPosition());

        probeRepository.saveAndFlush(probe);
    }

    public Probe find(Long id) {

        log.debug("[PROBE SERVICE] : Buscando sonda por id {}", id);

        var probe = probeRepository.findById(id)
                .orElseThrow(() -> new ProbeNotFoundException("Sonda não encontrada!"));

        return probe;

    }
}
