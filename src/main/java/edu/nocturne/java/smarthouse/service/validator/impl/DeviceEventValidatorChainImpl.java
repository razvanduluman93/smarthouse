package edu.nocturne.java.smarthouse.service.validator.impl;

import edu.nocturne.java.smarthouse.common.validation.ValidationNotification;
import edu.nocturne.java.smarthouse.domain.DeviceEvent;
import edu.nocturne.java.smarthouse.service.validator.DeviceEventValidator;
import edu.nocturne.java.smarthouse.service.validator.DeviceEventValidatorChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeviceEventValidatorChainImpl implements DeviceEventValidatorChain {

    private final List<DeviceEventValidator> deviceEventValidators;

    @Autowired
    public DeviceEventValidatorChainImpl(List<DeviceEventValidator> deviceEventValidators) {
        this.deviceEventValidators = deviceEventValidators;
    }

    @Override
    public void validate(DeviceEvent deviceEvent, ValidationNotification validationNotification) {
        deviceEventValidators.forEach(validator -> {
            if(validator.supportedCommands().contains(deviceEvent.getCommand())) {
                validator.validate(deviceEvent, validationNotification);
            }
        });
    }
}
