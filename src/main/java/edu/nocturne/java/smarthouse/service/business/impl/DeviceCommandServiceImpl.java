package edu.nocturne.java.smarthouse.service.business.impl;

import edu.nocturne.java.smarthouse.dao.DeviceEventsDao;
import edu.nocturne.java.smarthouse.domain.DeviceEvent;
import edu.nocturne.java.smarthouse.service.business.DeviceCommandService;
import edu.nocturne.java.smarthouse.service.business.DeviceEventProcessorChain;
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
