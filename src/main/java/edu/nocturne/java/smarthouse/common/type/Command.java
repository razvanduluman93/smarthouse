package edu.nocturne.java.smarthouse.common.type;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public enum Command {
    CREATE("CREATE"),
    UPDATE("UPDATE");

    private String value;

    Command(String value) {
        this.value=value;
    }
}
