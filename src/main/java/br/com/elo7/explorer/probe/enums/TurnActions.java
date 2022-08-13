package br.com.elo7.explorer.probe.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TurnActions {
    L('L'), R('R');

    private final char value;
    
}
