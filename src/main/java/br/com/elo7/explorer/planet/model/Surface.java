package br.com.elo7.explorer.planet.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Surface {

    @Column(name = "axis_x")
    private Integer axisX;

    @Column(name = "axis_Y")
    private Integer axisY;
}
