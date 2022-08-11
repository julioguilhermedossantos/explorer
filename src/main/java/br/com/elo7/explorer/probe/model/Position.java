package br.com.elo7.explorer.probe.model;

import lombok.*;

import javax.persistence.Embeddable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Position {

    private Integer coordinateX;
    private Integer coordinateY;
}
