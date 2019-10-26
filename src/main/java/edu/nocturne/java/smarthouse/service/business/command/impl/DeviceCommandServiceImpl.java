package edu.nocturne.java.smarthouse.service.business.command.impl;

import edu.nocturne.java.smarthouse.domain.DeviceEvent;
import edu.nocturne.java.smarthouse.service.business.command.DeviceCommandService;
import edu.nocturne.java.smarthouse.service.business.command.DeviceEventProcessorChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DeviceCommandServiceImpl implements DeviceCommandService {

    private final DeviceEventProcessorChain deviceEventProcessorChain;

    @Autowired
    public DeviceCommandServiceImpl(DeviceEventProcessorChain deviceEventProcessorChain) {
        this.deviceEventProcessorChain = deviceEventProcessorChain;
    }

    @Override
    public void putDevice(DeviceEvent deviceEvent) {
        deviceEventProcessorChain.process(deviceEvent);
    }

}
