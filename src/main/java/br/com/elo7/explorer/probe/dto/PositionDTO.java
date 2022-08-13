package br.com.elo7.explorer.probe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PositionDTO {

    @Positive
    private Integer coordinateX;
    @Positive
    private Integer coordinateY;

}
