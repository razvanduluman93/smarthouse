package edu.nocturne.java.smarthouse.type;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public enum DeviceState {
    ON("ON"),
    OFF("OFF");

    private String value;

    DeviceState(String value) {
        this.value=value;
    }
}
