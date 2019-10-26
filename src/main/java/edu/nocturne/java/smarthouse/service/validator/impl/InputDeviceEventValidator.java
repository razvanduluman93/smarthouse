package edu.nocturne.java.smarthouse.service.validator.impl;

import edu.nocturne.java.smarthouse.common.validation.ValidationException;
import edu.nocturne.java.smarthouse.common.validation.ValidationNotification;
import edu.nocturne.java.smarthouse.domain.DeviceEvent;
import edu.nocturne.java.smarthouse.service.validator.DeviceEventValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import static edu.nocturne.java.smarthouse.common.type.ErrorType.*;

@Component
public class InputDeviceEventValidator implements DeviceEventValidator {

    private static final Logger logger = LoggerFactory.getLogger(InputDeviceEventValidator.class);

    @Override
    public void validate(DeviceEvent deviceEvent, ValidationNotification validationNotification) {
        if (deviceEvent.getHouseReference() == null) {
            validationNotification.addError(EMPTY_HOUSE_REFERENCE);
        }
        if (deviceEvent.getDeviceReference() == null) {
            validationNotification.addError(EMPTY_DELIVERY_REFERENCE);
        }
        if (deviceEvent.getCommand() == null) {
            validationNotification.addError(EMPTY_COMMAND);
        }
        if (!validationNotification.hasNoErrors()) {
            logger.info(validationNotification.toString());
            throw new ValidationException(validationNotification);
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
