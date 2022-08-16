package br.com.elo7.explorer.probe.model;

import br.com.elo7.explorer.probe.enums.AllowedActions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Action {

    private AllowedActions[] actions;

}
