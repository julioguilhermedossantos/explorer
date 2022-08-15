package br.com.elo7.explorer.planet.model;

import br.com.elo7.explorer.probe.model.Position;
import br.com.elo7.explorer.probe.model.Probe;
import br.com.elo7.explorer.util.FileUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PlanetTest {

    private final Integer COORDINATE_X = 3;

    private final Integer COORDINATE_Y = 3;


    private String MARS = "MARS";

    @Test
    void hasOtherProbeLandedAt() {

        var position = FileUtil.fromJsonFile("position-x3-y3.json", Position.class);

        var surface = FileUtil.fromJsonFile("surface-x5-y5.json", Surface.class);

        var mars = FileUtil.fromJsonFile("planet-mars-null-surface-null-probes.json", Planet.class);

        var spaceX = FileUtil.fromJsonFile(
                "probe-space-x-null-position-null-north.json",
                Probe.class
        );

        var voyagerI = FileUtil.fromJsonFile(
                "probe-voyager-i-null-position-null-planet-point-to-east.json",
                Probe.class
        );

        mars.setSurface(surface);
        mars.getProbes().add(voyagerI);
        mars.getProbes().add(spaceX);

        voyagerI.setPlanet(mars);
        voyagerI.setPosition(position);

        spaceX.setPosition(position);
        spaceX.setPlanet(mars);

        var result = mars.hasOtherProbeLandedAt(position);

        assertTrue(result);

    }

}