package br.com.elo7.explorer.planet.dto;

import br.com.elo7.explorer.probe.dto.ProbeResponseDTO;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanetResponseDTO {


    private Long id;

    private String name;

    private SurfaceDTO surface;

    private Set<ProbeResponseDTO> probes;

}
