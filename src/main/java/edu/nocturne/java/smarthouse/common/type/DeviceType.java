package edu.nocturne.java.smarthouse.common.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.ToString;

import java.util.stream.Stream;

@ToString
@Getter
public enum DeviceType {
    TELEVISION("TELEVISION"),
    EMPTY("EMPTY");

    private String value;

    DeviceType(String value) {
        this.value = value;
    }

    @JsonCreator
    public static DeviceType deserialize(String value) {
        return Stream.of(values())
                     .filter(e -> e.name().equals(value))
                     .findFirst()
                     .orElse(EMPTY);
    }

}
