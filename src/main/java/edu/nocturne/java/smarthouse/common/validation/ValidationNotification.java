package edu.nocturne.java.smarthouse.common.validation;

import edu.nocturne.java.smarthouse.common.type.ErrorType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
public class ValidationNotification implements Serializable {

    private List<ErrorType> errors = new ArrayList<>();

    public void addError(ErrorType error) {
        errors.add(error);
    }

    public boolean hasNoErrors() {
        return errors.isEmpty();
    }
}
