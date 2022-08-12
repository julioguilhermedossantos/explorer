package br.com.elo7.explorer.advice.excepion;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnknownPlanetException extends RuntimeException{

    public UnknownPlanetException(String message) {
        super(message);
    }
}
