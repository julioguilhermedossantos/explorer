package br.com.elo7.explorer.probe.model;

import br.com.elo7.explorer.probe.enums.PointTo;
import br.com.elo7.explorer.util.FileUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProbeTest {

    @Test
    @DisplayName("Should turn left: North -> West, West -> South, South -> East and East -> North")
    void execute() {

        var turn = 'L';

        var probe = getProvePositionX3Y3();

        var pointToBeforeAction = probe.getPointTo();

        assertEquals(PointTo.NORTH, pointToBeforeAction);

        probe.execute(turn);
        assertEquals(PointTo.WEST, probe.getPointTo());

        probe.execute(turn);
        assertEquals(PointTo.SOUTH, probe.getPointTo());

        probe.execute(turn);
        assertEquals(PointTo.EAST, probe.getPointTo());

        probe.execute(turn);
        assertEquals(pointToBeforeAction, probe.getPointTo());


    }

    @Test
    @DisplayName("Should move +1 position on Y axis")
    void execute2() {

        var move = 'M';

        var expectedCoordinateY = 4;

        var probe = getProvePositionX3Y3();

        probe.execute(move);

        assertEquals(expectedCoordinateY, probe.getPosition().getCoordinateY());

    }

    @Test
    @DisplayName("Should move -1 position on Y axis")
    void execute3() {

        var move = 'M';
        var turns = new char[]{'L', 'L'};

        var expectedCoordinateY = 2;

        var probe = getProvePositionX3Y3();

        for (char turn : turns) {
            probe.execute(turn);
        }

        probe.execute(move);

        assertEquals(expectedCoordinateY, probe.getPosition().getCoordinateY());

    }

    @Test
    @DisplayName("Should move +1 position on X axis")
    void execute4() {

        var move = 'M';
        var turns = new char[]{'L', 'L', 'L'};

        var expectedCoordinateX = 4;

        var probe = getProvePositionX3Y3();

        for (char turn : turns) {
            probe.execute(turn);
        }

        probe.execute(move);
        assertEquals(expectedCoordinateX, probe.getPosition().getCoordinateX());

    }

    @Test
    @DisplayName("Should move -1 position on X axis")
    void execute5() {

        var move = 'M';
        var turn = 'L';

        var expectedCoordinateX = 2;

        var probe = getProvePositionX3Y3();

        probe.execute(turn);
        probe.execute(move);

        assertEquals(expectedCoordinateX, probe.getPosition().getCoordinateX());

    }

    private Probe getProvePositionX3Y3() {

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