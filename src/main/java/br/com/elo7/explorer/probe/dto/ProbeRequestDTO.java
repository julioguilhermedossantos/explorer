package br.com.elo7.explorer.probe.dto;


import br.com.elo7.explorer.probe.enums.PointTo;
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
public class ProbeRequestDTO {

    @NotEmpty
    private String name;

    @Valid
    private PositionDTO position;

    private PointTo pointingTo;

    private Long planetId;

}
