package br.com.elo7.explorer.advice.excepion;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollisionExpection extends RuntimeException {

    public CollisionExpection(String message) {
        super(message);
    }
}
