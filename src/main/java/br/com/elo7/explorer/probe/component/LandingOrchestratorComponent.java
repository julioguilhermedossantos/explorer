package br.com.elo7.explorer.probe.component;

import br.com.elo7.explorer.advice.excepion.CollisionExpection;
import br.com.elo7.explorer.advice.excepion.OrbitalLimitExceededException;
import br.com.elo7.explorer.advice.excepion.UnknownPlanetException;
import br.com.elo7.explorer.planet.repository.PlanetRepository;
import br.com.elo7.explorer.probe.dto.LandProbeDTO;
import br.com.elo7.explorer.probe.model.Position;
import br.com.elo7.explorer.probe.repository.ProbeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;

@Component
@RequiredArgsConstructor
public class LandingOrchestratorComponent {

    private final PlanetRepository planetRepository;
    private final ProbeRepository probeRepository;
    private final ObjectMapper objectMapper;

    public void langProbe(LandProbeDTO landProbeDTO) {
        var requiredPosition = objectMapper.convertValue(landProbeDTO.getPosition(), Position.class);

        var targetPlanet = planetRepository.findByName(String.valueOf(landProbeDTO.getPlanetId()))
                .orElseThrow(() -> new UnknownPlanetException("Planeta não registrado!"));

        if (targetPlanet.isRequiredPositionExceedingOrbitalLimit(requiredPosition))
            throw new OrbitalLimitExceededException("Fora da óbita!");

        if (targetPlanet.hasProbeLandedAt(requiredPosition))
            throw new CollisionExpection(
                    String.format("Sonda pousada na posição x (%s) e y (%s)!",
                            requiredPosition.getCoordinateX(), requiredPosition.getCoordinateY())
            );

        var landingProbe = probeRepository.findById(landProbeDTO.getProbeId())
                .orElseThrow(() -> new NotFoundException("Sonda não encontrada"));

        landingProbe.setPosition(requiredPosition);
        probeRepository.saveAndFlush(landingProbe);
    }
}
