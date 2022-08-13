package br.com.elo7.explorer.advice.excepion;

public class NotAllowedActionException extends RuntimeException {

    public NotAllowedActionException(String message) {
        super(message);
    }
}