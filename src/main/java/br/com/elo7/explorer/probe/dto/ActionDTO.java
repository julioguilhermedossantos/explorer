package br.com.elo7.explorer.probe.dto;

import br.com.elo7.explorer.advice.excepion.NotAllowedActionException;
import br.com.elo7.explorer.probe.enums.AllowedActions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;

@Getter
@Setter
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class ActionDTO {

    @NotEmpty
    @NotNull
    private String action;

    public boolean validate(){
        for (char act : action.toUpperCase().toCharArray()){
            if(!isAllowed(act))
                throw new NotAllowedActionException("Ação desconhecida!");
        }

        return true;
    }

    private boolean isAllowed(char act){
        return act == AllowedActions.L.getValue() || act == AllowedActions.R.getValue() || act == AllowedActions.M.getValue();
    }
}
