package br.com.elo7.explorer.probe.dto;

import br.com.elo7.explorer.planet.dto.PlanetResponseDTO;
import br.com.elo7.explorer.probe.enums.PointTo;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProbeResponseDTO {

    private Long id;

    private String name;

    private PositionDTO position;

    private PointTo pointTo;

    private PlanetResponseDTO planet;
}