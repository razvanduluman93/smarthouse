package edu.nocturne.java.smarthouse.service.listener.impl;

import edu.nocturne.java.smarthouse.domain.DeviceEvent;
import edu.nocturne.java.smarthouse.service.business.command.DeviceCommandService;
import edu.nocturne.java.smarthouse.service.listener.DeviceEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
public class DeviceEventListenerImpl implements DeviceEventListener {

    private final DeviceCommandService deviceCommandService;

    @Autowired
    public DeviceEventListenerImpl(DeviceCommandService deviceCommandService) {
        this.deviceCommandService = deviceCommandService;
    }

    @SqsListener("${cloud.aws.queues.DeviceEventsQueue}")
    public void process(DeviceEvent deviceEvent) {
        deviceCommandService.putDevice(deviceEvent);
    }

}
