package br.com.elo7.explorer.planet.model;

import br.com.elo7.explorer.probe.model.Probe;
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

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="planet_id")
    private Set<Probe> exploringProbes;
}
