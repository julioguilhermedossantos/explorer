package br.com.elo7.explorer.planet.model;

import br.com.elo7.explorer.advice.excepion.CollisionExpection;
import br.com.elo7.explorer.advice.excepion.OrbitalLimitExceededException;
import br.com.elo7.explorer.probe.enums.PointTo;
import br.com.elo7.explorer.probe.model.Position;
import br.com.elo7.explorer.probe.model.Probe;
import lombok.*;

import javax.persistence.*;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Planet {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "planet_sequence")
    @SequenceGenerator(name = "planet_sequence", sequenceName = "planet_seq")
    private Long id;

    private String name;

    private PointTo pointTo;

    @Embedded
    private Surface surface;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="planet_id")
    private Set<Probe> exploringProbes;

    @SneakyThrows
    public Boolean hasProbeLandedAt(Position position){
        return exploringProbes.stream()
                .parallel()
                .anyMatch(probe -> probe.getPosition() == position);
    }

    public Boolean isRequiredPositionExceedingOrbitalLimit(Position position){
        return position.getCoordinateX() > surface.getAxleX() || position.getCoordinateY() > surface.getAxleY();
    }
}
