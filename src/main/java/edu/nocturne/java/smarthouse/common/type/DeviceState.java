package edu.nocturne.java.smarthouse.common.type;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public enum DeviceState {
    ON("ON"),
    OFF("OFF"),
    EMPTY("EMPTY");

    private String value;

    DeviceState(String value) {
        this.value=value;
    }
}
