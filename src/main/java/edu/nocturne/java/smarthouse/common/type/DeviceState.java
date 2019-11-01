package edu.nocturne.java.smarthouse.common.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.ToString;

import java.util.stream.Stream;

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

    @JsonCreator
    public static DeviceState deserialize(String value) {
        return Stream.of(values())
                     .filter(e -> e.name().equals(value))
                     .findFirst()
                     .orElse(EMPTY);
    }
}
