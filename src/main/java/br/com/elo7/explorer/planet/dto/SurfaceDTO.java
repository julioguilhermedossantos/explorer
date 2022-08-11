package br.com.elo7.explorer.planet.dto;

import lombok.*;

import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurfaceDTO {

    @Positive
    private Integer axleX;
    @Positive
    private Integer axleY;
}