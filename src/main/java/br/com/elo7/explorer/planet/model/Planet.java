package br.com.elo7.explorer.planet.model;

import br.com.elo7.explorer.probe.model.Position;
import br.com.elo7.explorer.probe.model.Probe;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

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

    @Embedded
    private Surface surface;

    @JsonManagedReference
    @JoinColumn(name="planet_id")
    @OneToMany
    private Set<Probe> exploringProbes;

    public Boolean hasProbeLandedAt(Position position){
        return exploringProbes.stream()
                .parallel()
                .anyMatch(probe -> probe.getPosition() == position);
    }

    public Boolean isRequiredPositionExceedingOrbitalLimit(Position position){
        return position.getCoordinateX() > surface.getAxisX() || position.getCoordinateY() > surface.getAxisY();
    }
}
