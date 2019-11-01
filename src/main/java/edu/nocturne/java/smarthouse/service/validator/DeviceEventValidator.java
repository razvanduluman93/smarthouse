package edu.nocturne.java.smarthouse.service.validator;

import edu.nocturne.java.smarthouse.common.type.Command;
import edu.nocturne.java.smarthouse.common.validation.ValidationNotification;
import edu.nocturne.java.smarthouse.domain.DeviceEvent;
import org.springframework.core.Ordered;

import java.util.List;

public interface DeviceEventValidator extends Ordered {

    void validate(DeviceEvent deviceEvent, ValidationNotification validationNotification);

    List<Command> supportedCommands();

    @Override
    default int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
