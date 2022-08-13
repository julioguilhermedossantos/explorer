package br.com.elo7.explorer.advice.excepion;

public class ProbeNotFound extends RuntimeException{

    public ProbeNotFound(String message) {
        super(message);
    }
}
