package br.com.elo7.explorer.planet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class PlanetRequestDTO {

    @NotEmpty
    private String name;

    @Valid
    private SurfaceDTO surface;

}
