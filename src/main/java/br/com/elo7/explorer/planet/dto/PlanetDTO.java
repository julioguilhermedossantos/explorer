package br.com.elo7.explorer.planet.dto;

import br.com.elo7.explorer.probe.dto.ProbeDTO;
import br.com.elo7.explorer.probe.enums.PointTo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Getter
@Setter
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class PlanetDTO {

    @Null
    private Long id;

    @NotEmpty
    private String name;

    @Valid
    private SurfaceDTO surface;

    @Null
    private Set<ProbeDTO> exploringProbes;
}
