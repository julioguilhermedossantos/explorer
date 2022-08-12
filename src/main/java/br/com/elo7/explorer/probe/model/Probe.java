package br.com.elo7.explorer.probe.model;

import br.com.elo7.explorer.planet.model.Planet;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

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

    private String pointingTo;

    @JsonBackReference
    @JoinColumn(name = "planet_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Planet currentExlporingPlanet;
}
