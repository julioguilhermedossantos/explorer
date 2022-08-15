package br.com.elo7.explorer.probe.model;

import br.com.elo7.explorer.probe.enums.PointTo;
import br.com.elo7.explorer.util.FileUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProbeTest {

    @Test
    @DisplayName("Should turn left: North -> West")
    void execute() {

        var action = 'L';

        var probe = getProvePositionX3Y3();

        var pointToBeforeAction = probe.getPointTo();

        probe.execute(action);

        assertEquals(pointToBeforeAction, PointTo.NORTH);
        assertEquals(probe.getPointTo(), PointTo.WEST);

    }

    @Test
    @DisplayName("Should move +1 position on Y axis")
    void execute2() {

        var action = 'M';

        var expectedCoordinateY = 4;

        var probe = getProvePositionX3Y3();

        probe.execute(action);

        assertEquals(expectedCoordinateY, probe.getPosition().getCoordinateY());

    }

    private Probe getProvePositionX3Y3(){

        var probe = FileUtil.fromJsonFile(
                "probe-voyager-i-null-position-null-planet-point-to-east.json",
                Probe.class
        );

        var position = FileUtil.fromJsonFile(
                "position-x3-y3.json",
                Position.class
        );

        probe.setPosition(position);

        return probe;
    }
}