package br.com.elo7.explorer.probe.enums;

import lombok.Getter;

@Getter
public enum AllowedActions {
    L('L'), R('R'), M('M');

    private final Character value;

    AllowedActions(Character value) {
        this.value = value;
    }
}
