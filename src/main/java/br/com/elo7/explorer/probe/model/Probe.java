package br.com.elo7.explorer.probe.model;

import br.com.elo7.explorer.planet.model.Planet;
import br.com.elo7.explorer.probe.enums.PointTo;
import br.com.elo7.explorer.probe.enums.TurnActions;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Probe {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "probe_sequence")
    @SequenceGenerator(name = "probe_sequence", sequenceName = "probe_seq")
    private Long id;

    private String name;

    @Embedded
    private Position position;

    @Enumerated(EnumType.STRING)
    private PointTo pointingTo;

    @JsonBackReference
    @JoinColumn(name = "planet_id")
    @ManyToOne
    private Planet currentExlporingPlanet;

    public void execute(char action) {
        if (isTurnAction(action)) {
            turn(action);
        } else {
            move();
        }
    }

    private void move() {
        if (isVerticalMove()) {
            position.verticalMove(pointingTo.getStep());
        } else {
            position.horizontalMove(pointingTo.getStep());
        }
    }

    private boolean isVerticalMove() {
        return List.of(PointTo.NORTH, PointTo.SOUTH).contains(pointingTo);
    }

    private boolean isTurnAction(char action) {
        return Arrays.stream(TurnActions.values())
                .map(TurnActions::getValue)
                .anyMatch(turnActions -> action == turnActions);
    }

    private void turn(char action) {
        setPointingTo(pointingTo.changeOrientation(action));
    }

}
