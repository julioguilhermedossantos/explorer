package br.com.elo7.explorer.planet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurfaceDTO {

    @Positive
    private Integer axisX;

    @Positive
    private Integer axisY;

}
