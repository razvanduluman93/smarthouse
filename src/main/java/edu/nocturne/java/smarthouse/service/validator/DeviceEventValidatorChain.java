package edu.nocturne.java.smarthouse.service.validator;

import edu.nocturne.java.smarthouse.common.validation.ValidationNotification;
import edu.nocturne.java.smarthouse.domain.DeviceEvent;

public interface DeviceEventValidatorChain {
    void validate(DeviceEvent deviceEvent, ValidationNotification validationNotification);
}
