package edu.nocturne.java.smarthouse.service.business.command;

import edu.nocturne.java.smarthouse.domain.DeviceEvent;
import edu.nocturne.java.smarthouse.common.type.Command;

public interface DeviceEventProcessor {

    void process(DeviceEvent deviceEventDTO);

    Command getCommand();

}
