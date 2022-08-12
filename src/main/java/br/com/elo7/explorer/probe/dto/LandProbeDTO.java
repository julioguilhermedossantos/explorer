package br.com.elo7.explorer.probe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class LandProbeDTO {

    @NotNull
    private Long planetId;
    @NotNull
    private Long probeId;
    @Valid
    private PositionDTO position;
}
