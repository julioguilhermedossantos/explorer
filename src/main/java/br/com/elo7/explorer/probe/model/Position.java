package br.com.elo7.explorer.probe.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter
@Builder
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Position {

    @Column(name = "coordinate_x")
    private Integer coordinateX;

    @Column(name = "coordinate_y")
    private Integer coordinateY;

    public void verticalMove (Integer amount){
        coordinateY += amount;
    }

    public void horizontalMove (Integer amount){
        coordinateX += amount;
    }
}
