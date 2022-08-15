package br.com.elo7.explorer.probe.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Action {

    private String action;

    public char[] split(){

        return action.toUpperCase().toCharArray();

    }

}
