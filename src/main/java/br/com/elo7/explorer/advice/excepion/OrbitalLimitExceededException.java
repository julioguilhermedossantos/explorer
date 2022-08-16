package br.com.elo7.explorer.advice.excepion;

public class OrbitalLimitExceededException extends RuntimeException {

    public OrbitalLimitExceededException(String message) {
        super(message);
    }

}
