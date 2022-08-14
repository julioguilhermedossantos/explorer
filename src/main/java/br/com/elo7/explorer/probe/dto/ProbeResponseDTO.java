package br.com.elo7.explorer.probe.dto;

import br.com.elo7.explorer.planet.dto.PlanetResponseDTO;
import br.com.elo7.explorer.probe.enums.PointTo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class ProbeResponseDTO {

    private Long id;

    private String name;

    private PositionDTO position;

    private PointTo pointingTo;

    private PlanetResponseDTO planet;

}
