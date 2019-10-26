package edu.nocturne.java.smarthouse.service.validator;

import edu.nocturne.java.smarthouse.common.validation.ValidationNotification;
import edu.nocturne.java.smarthouse.domain.DeviceEvent;
import org.springframework.core.Ordered;

public interface DeviceEventValidator extends Ordered {

    void validate(DeviceEvent deviceEvent, ValidationNotification validationNotification);

    @Override
    default int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
