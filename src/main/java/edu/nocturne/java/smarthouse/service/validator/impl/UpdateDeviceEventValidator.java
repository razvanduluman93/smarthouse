package edu.nocturne.java.smarthouse.service.validator.impl;

import edu.nocturne.java.smarthouse.common.type.Command;
import edu.nocturne.java.smarthouse.common.validation.ValidationNotification;
import edu.nocturne.java.smarthouse.dao.DeviceEventsDao;
import edu.nocturne.java.smarthouse.domain.DeviceEvent;
import edu.nocturne.java.smarthouse.service.validator.DeviceEventValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static edu.nocturne.java.smarthouse.common.type.ErrorType.ALREADY_EXISTENT_PRIMARY_KEY;
import static edu.nocturne.java.smarthouse.common.type.ErrorType.INEXISTENT_DEVICE;

@Component
public class UpdateDeviceEventValidator implements DeviceEventValidator {

    private final DeviceEventsDao deviceEventsDao;

    @Autowired
    public UpdateDeviceEventValidator(DeviceEventsDao deviceEventsDao) {
        this.deviceEventsDao = deviceEventsDao;
    }

    @Override
    public void validate(DeviceEvent deviceEvent, ValidationNotification validationNotification) {
        if (deviceEventsDao.getDeviceEvents(deviceEvent.getHouseReference(), deviceEvent.getDeviceReference()).isEmpty()) {
            validationNotification.addError(INEXISTENT_DEVICE);
        }
    }

    @Override
    public List<Command> supportedCommands() {
        return Arrays.asList(Command.UPDATE);
    }
}
