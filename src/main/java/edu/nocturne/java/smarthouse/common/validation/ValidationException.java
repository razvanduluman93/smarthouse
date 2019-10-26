package edu.nocturne.java.smarthouse.common.validation;

public class ValidationException extends RuntimeException {

    private ValidationNotification validationNotification;

    public ValidationException(ValidationNotification validationNotification) {
        this.validationNotification = validationNotification;
    }

}
