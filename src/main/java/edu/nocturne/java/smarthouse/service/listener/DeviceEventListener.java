package edu.nocturne.java.smarthouse.service.listener;

import edu.nocturne.java.smarthouse.domain.DeviceEvent;
import edu.nocturne.java.smarthouse.service.business.command.DeviceCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
public class DeviceEventListener {

    @Value("${cloud.aws.queues.DeviceEventsQueue}")
    private String queueName;

    private final DeviceCommandService deviceCommandService;

    @Autowired
    public DeviceEventListener(DeviceCommandService deviceCommandService) {
        this.deviceCommandService = deviceCommandService;
    }

    @SqsListener("event")
    public void process(DeviceEvent deviceEvent){
        deviceCommandService.putDevice(deviceEvent);
    }

}
