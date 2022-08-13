package br.com.elo7.explorer.planet.model;

import lombok.*;

import javax.persistence.Embeddable;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Surface {

    private Integer axisX;
    private Integer axisY;
}
