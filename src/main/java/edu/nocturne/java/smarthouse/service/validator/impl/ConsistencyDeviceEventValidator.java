package edu.nocturne.java.smarthouse.service.validator.impl;

import edu.nocturne.java.smarthouse.common.validation.ValidationNotification;
import edu.nocturne.java.smarthouse.dao.DeviceEventsDao;
import edu.nocturne.java.smarthouse.domain.DeviceEvent;
import edu.nocturne.java.smarthouse.service.validator.DeviceEventValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static edu.nocturne.java.smarthouse.common.type.ErrorType.ALREADY_EXISTENT_PRIMARY_KEY;

@Component
public class ConsistencyDeviceEventValidator implements DeviceEventValidator {


    private final DeviceEventsDao deviceEventsDao;

    @Autowired
    public ConsistencyDeviceEventValidator(DeviceEventsDao deviceEventsDao) {
        this.deviceEventsDao = deviceEventsDao;
    }

    @Override
    public void validate(DeviceEvent deviceEvent, ValidationNotification validationNotification) {
        if (!deviceEventsDao.getDeviceEvents(deviceEvent.getHouseReference(), deviceEvent.getDeviceReference()).isEmpty()) {
            validationNotification.addError(ALREADY_EXISTENT_PRIMARY_KEY);
        }
    }

}
