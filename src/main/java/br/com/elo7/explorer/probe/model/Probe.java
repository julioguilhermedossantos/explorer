package br.com.elo7.explorer.probe.model;

import br.com.elo7.explorer.planet.model.Planet;
import br.com.elo7.explorer.probe.enums.PointTo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.stream.Stream;

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

    public void execute(Character action) {
        var turnActions = new char[]{'L', 'R'};

        if (Stream.of(turnActions).anyMatch(action::equals)) {
            pointingTo.changeOrientation(action);
        }else {
            move();
        }
    }

    private void move() {
        var verticalMove = new PointTo[]{PointTo.NORTH, PointTo.SOUTH};
        if (Stream.of(verticalMove).anyMatch(pointTo -> pointTo == pointingTo)) {
            position.verticalMove(pointingTo.getStep());
        } else {
            position.horizontalMove(pointingTo.getStep());
        }
    }
}
