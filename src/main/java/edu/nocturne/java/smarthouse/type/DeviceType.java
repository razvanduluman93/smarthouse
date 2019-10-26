package edu.nocturne.java.smarthouse.type;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public enum DeviceType {
    TELEVISION("TELEVISION");

    private String value;

    DeviceType(String value) {
        this.value = value;
    }
}
