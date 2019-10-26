package edu.nocturne.java.smarthouse.service.business.command.impl;

import edu.nocturne.java.smarthouse.domain.DeviceEvent;
import edu.nocturne.java.smarthouse.service.business.command.DeviceCommandService;
import edu.nocturne.java.smarthouse.service.business.command.DeviceEventDispacherChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DeviceCommandServiceImpl implements DeviceCommandService {

    private final DeviceEventDispacherChain deviceEventDispacherChain;

    @Autowired
    public DeviceCommandServiceImpl(DeviceEventDispacherChain deviceEventDispacherChain) {
        this.deviceEventDispacherChain = deviceEventDispacherChain;
    }

    @Override
    public void putDevice(DeviceEvent deviceEvent) {
        deviceEventDispacherChain.process(deviceEvent);
    }

}
