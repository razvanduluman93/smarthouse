package edu.nocturne.java.smarthouse.common.validation;

import edu.nocturne.java.smarthouse.common.type.ErrorType;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public class ValidationNotification {

    private List<ErrorType> errors = new ArrayList<>();

    public void addError(ErrorType error) {
        errors.add(error);
    }

    public boolean hasNoErrors() {
        return errors.isEmpty();
    }
}
