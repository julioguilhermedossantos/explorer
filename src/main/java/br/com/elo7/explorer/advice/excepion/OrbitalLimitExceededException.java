package br.com.elo7.explorer.advice.excepion;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrbitalLimitExceededException extends RuntimeException {

    public OrbitalLimitExceededException(String message) {
        super(message);
    }
}
