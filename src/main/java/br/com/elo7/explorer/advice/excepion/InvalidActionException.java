package br.com.elo7.explorer.advice.excepion;

public class InvalidActionException extends RuntimeException{
    public InvalidActionException(String message) {
        super(message);
    }
}
