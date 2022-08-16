package br.com.elo7.explorer.probe.service;

import br.com.elo7.explorer.advice.excepion.CollisionExpection;
import br.com.elo7.explorer.advice.excepion.OrbitalLimitExceededException;
import br.com.elo7.explorer.planet.model.Planet;
import br.com.elo7.explorer.planet.model.Surface;
import br.com.elo7.explorer.planet.service.PlanetService;
import br.com.elo7.explorer.probe.enums.AllowedActions;
import br.com.elo7.explorer.probe.enums.PointTo;
import br.com.elo7.explorer.probe.model.Action;
import br.com.elo7.explorer.probe.model.Position;
import br.com.elo7.explorer.probe.model.Probe;
import br.com.elo7.explorer.probe.repository.ProbeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProbeServiceTest {
    @Mock
    private Position position;
    @Mock
    private Probe probe;

    @Mock
    private ProbeRepository probeRepository;

    @Mock
    private PlanetService planetService;

    @InjectMocks
    private ProbeService probeService;

    private final String PROBE_NAME = "Voyager I";
    private final String PROBE_NAME2 = "Space x";
    private final Long ID = 1L;
    private final Integer AXIS_X = 3;
    private final Integer AXIS_Y = 3;

    private final Integer AXIS_X2 = 5;
    private final Integer AXIS_Y2 = 5;

    @Test
    void create() {

        var planet = Planet.builder()
                .name("Mars")
                .surface(Surface.builder().axisX(AXIS_X).axisY(AXIS_Y).build())
                .id(ID)
                .probes(new HashSet<Probe>())
                .build();

        var spy = Mockito.spy(planet);

        when(probe.getName()).thenReturn(PROBE_NAME);
        when(probe.getPosition()).thenReturn(position);
        when(position.getCoordinateX()).thenReturn(AXIS_X);
        when(position.getCoordinateY()).thenReturn(AXIS_Y);

        when(planetService.findById(ID)).thenReturn(spy);

        doAnswer(invocation -> {
            assertEquals(invocation.getArgument(0), probe);
            return null;
        }).when(probeRepository).saveAndFlush(probe);

        probeService.create(probe, ID);

       assertFalse(spy.getProbes().isEmpty());

    }

    @Test
    void create2() {

        var planet = Planet.builder()
                .name("Mars")
                .surface(Surface.builder().axisX(AXIS_X).axisY(AXIS_Y).build())
                .id(ID)
                .probes(new HashSet<Probe>())
                .build();

        var planetSpy = Mockito.spy(planet);

        when(probe.getName()).thenReturn(PROBE_NAME);
        when(probe.getPosition()).thenReturn(position);
        when(position.getCoordinateX()).thenReturn(AXIS_X2);


        when(planetService.findById(ID)).thenReturn(planetSpy);

        assertThrows(OrbitalLimitExceededException.class, () -> {
            probeService.create(probe, ID);
        });

    }

    @Test
    void create3() {

        var probes = new HashSet<Probe>();

        var planet = Planet.builder()
                .name("Mars")
                .surface(Surface.builder().axisX(AXIS_X2).axisY(AXIS_Y2).build())
                .id(ID)
                .build();

        var sharedPosition = Position.builder()
                .coordinateX(AXIS_X)
                .coordinateY(AXIS_Y)
                .build();

        probes.add(
                Probe.builder()
                        .name(PROBE_NAME)
                        .position(sharedPosition)
                        .id(ID)
                        .pointTo(PointTo.NORTH)
                        .planet(planet)
                        .build()
        );

        planet.setProbes(probes);

        var newProbe = Probe.builder()
                .name(PROBE_NAME2)
                .position(sharedPosition)
                .pointTo(PointTo.WEST)
                .build();

        var planetSpy = Mockito.spy(planet);
        var probeSpy = Mockito.spy(newProbe);

        when(planetService.findById(ID)).thenReturn(planetSpy);

        assertThrows(CollisionExpection.class, () -> {
            probeService.create(probeSpy, ID);
        });

    }


    @Test
    void move() {

        var actions = new AllowedActions[]{AllowedActions.M, AllowedActions.L};

        var action = new Action(actions);

        var actionSpy = Mockito.spy(action);

        var planet = Planet.builder()
                .name("Mars")
                .surface(Surface.builder().axisX(AXIS_X).axisY(AXIS_Y).build())
                .id(ID)
                .probes(new HashSet<Probe>())
                .build();

        var planetSpy = Mockito.spy(planet);


        var position = Position.builder()
                .coordinateX(AXIS_X)
                .coordinateY(AXIS_Y)
                .build();

        var probe = Probe.builder()
                .name(PROBE_NAME)
                .position(position)
                .pointTo(PointTo.WEST)
                .planet(planetSpy)
                .build();

        var probeSpy = Mockito.spy(probe);

        planetSpy.getProbes().add(probe);

        when(probeRepository.findById(ID)).thenReturn(Optional.of(probeSpy));

        probeService.move(actionSpy, ID);

        assertEquals(2, (int) probeSpy.getPosition().getCoordinateX());

        assertEquals(PointTo.SOUTH, probeSpy.getPointTo());
    }

}