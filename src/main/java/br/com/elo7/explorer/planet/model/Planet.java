package br.com.elo7.explorer.planet.model;

import br.com.elo7.explorer.advice.excepion.CollisionExpection;
import br.com.elo7.explorer.advice.excepion.OrbitalLimitExceededException;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "planet_sequence")
    @SequenceGenerator(name = "planet_sequence", sequenceName = "planet_seq")
    private Long id;

    private String name;

    @Embedded
    private Surface surface;

    @JsonManagedReference
    @JoinColumn(name = "planet_id")
    @OneToMany
    private Set<Probe> probes;

    public Boolean hasOtherProbeLandedAt(Position position) {
        return probes.stream()
                .filter(probe -> probe.getPosition().equals(position)).count() > 1;
    }

    public Boolean isRequiredPositionExceedingOrbitalLimit(Position position) {
        return position.getCoordinateX() > surface.getAxisX() || position.getCoordinateY() > surface.getAxisY();
    }

    public void validate(Position position){

        if (isRequiredPositionExceedingOrbitalLimit(position))
            throw new OrbitalLimitExceededException("Fora do limite orbital!");

        if (hasOtherProbeLandedAt(position))
            throw new CollisionExpection("Existe uma sonda pousada nesta posição!");

    }

    public void addProbe(Probe probe){
        probes.add(probe);
        validate(probe.getPosition());
    }
}
