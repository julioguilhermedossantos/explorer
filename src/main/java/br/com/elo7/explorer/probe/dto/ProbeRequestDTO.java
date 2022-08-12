package br.com.elo7.explorer.probe.dto;


import br.com.elo7.explorer.probe.enums.PointTo;
import lombok.*;
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

    private String pointTo;

    private Long planetId;

    @SneakyThrows
    public void setPointTo(String value) {
        this.pointTo = PointTo.valueOf(value.toUpperCase()).name();
    }
}
