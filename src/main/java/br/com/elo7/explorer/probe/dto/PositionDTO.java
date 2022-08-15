package br.com.elo7.explorer.probe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PositionDTO {

    @PositiveOrZero
    private Integer coordinateX;

    @PositiveOrZero
    private Integer coordinateY;

}
