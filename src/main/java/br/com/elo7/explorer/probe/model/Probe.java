package br.com.elo7.explorer.probe.model;

import br.com.elo7.explorer.planet.model.Planet;
import br.com.elo7.explorer.probe.enums.AllowedActions;
import br.com.elo7.explorer.probe.enums.PointTo;
import br.com.elo7.explorer.probe.enums.TurnActions;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Probe {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "probe_sequence")
    @SequenceGenerator(name = "probe_sequence", sequenceName = "probe_seq")
    private Long id;

    private String name;

    @Embedded
    private Position position;

    @Enumerated(EnumType.STRING)
    private PointTo pointTo;

    @JsonBackReference
    @JoinColumn(name = "planet_id")
    @ManyToOne
    private Planet planet;

    public void execute(AllowedActions action) {
        if (isTurnAction(action)) {
            turn(action);
        } else {
            move();
        }
    }

    private void move() {
        if (isVerticalMove()) {
            position.verticalMove(pointTo.getStep());

        } else {
            position.horizontalMove(pointTo.getStep());

        }
        planet.validate(position);
    }

    private boolean isVerticalMove() {
        return List.of(PointTo.NORTH, PointTo.SOUTH).contains(pointTo);
    }

    private boolean isTurnAction(AllowedActions action) {
        return Arrays.stream(TurnActions.values())
                .map(TurnActions::getValue)
                .anyMatch(turnActions -> action.getValue() == turnActions);
    }

    private void turn(AllowedActions actions) {
        setPointTo(pointTo.changeOrientation(actions));
    }

}
