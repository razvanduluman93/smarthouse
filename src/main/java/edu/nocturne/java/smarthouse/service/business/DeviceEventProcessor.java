package edu.nocturne.java.smarthouse.service.business;

import edu.nocturne.java.smarthouse.domain.DeviceEvent;
import edu.nocturne.java.smarthouse.type.Command;

public interface DeviceEventProcessor {

    void process(DeviceEvent deviceEventDTO);

    Command getCommand();

}
