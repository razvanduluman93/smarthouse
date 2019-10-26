package edu.nocturne.java.smarthouse.common.type;

import lombok.ToString;

@ToString
public enum ErrorType {
    EMPTY_HOUSE_REFERENCE,
    EMPTY_DELIVERY_REFERENCE,
    EMPTY_COMMAND,
    ALREADY_EXISTENT_PRIMARY_KEY
}
