package br.com.elo7.explorer.advice.excepion;

public class ProbeNotFoundException extends RuntimeException{

    public ProbeNotFoundException(String message) {
        super(message);
    }
}
