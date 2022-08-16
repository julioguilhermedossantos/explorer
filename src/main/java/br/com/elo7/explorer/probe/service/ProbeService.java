package br.com.elo7.explorer.probe.service;

import br.com.elo7.explorer.advice.excepion.ProbeNotFoundException;
import br.com.elo7.explorer.planet.service.PlanetService;
import br.com.elo7.explorer.probe.enums.AllowedActions;
import br.com.elo7.explorer.probe.model.Action;
import br.com.elo7.explorer.probe.model.Probe;
import br.com.elo7.explorer.probe.repository.ProbeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProbeService {

    private final ProbeRepository probeRepository;
    private final PlanetService planetService;


    public Page<Probe> list(PageRequest pageRequest) {

        log.debug("[PROBE SERVICE] : Listando sondas");

        return probeRepository.findAll(pageRequest);

    }

    public void create(Probe probe, Long planetId) {

        log.debug("[PROBE SERVICE] : Criando sonda {}", probe.getName());

        var planet = planetService.findById(planetId);

        planet.addProbe(probe);

        probeRepository.saveAndFlush(probe);

    }

    public void move(Action actions, Long probeId) {

        log.debug("[PROBE SERVICE] : Movendo sonda");

        var probe = probeRepository.findById(probeId)
                .orElseThrow(() -> new ProbeNotFoundException("Sonda não encontrada!"));

        for (AllowedActions act : actions.getActions()) {

            probe.execute(act);

        }

        probe.getPlanet().validate(probe.getPosition());

        probeRepository.saveAndFlush(probe);

    }

    public Probe findById(Long id) {

        log.debug("[PROBE SERVICE] : Buscando sonda por id {}", id);

       return probeRepository.findById(id)
                .orElseThrow(() -> new ProbeNotFoundException("Sonda não encontrada!"));

    }
}
