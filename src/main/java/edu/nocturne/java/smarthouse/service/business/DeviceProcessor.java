package edu.nocturne.java.smarthouse.service.business;

import edu.nocturne.java.smarthouse.domain.Device;
import edu.nocturne.java.smarthouse.type.Command;

public interface DeviceProcessor {

    void process(Device deviceEventDTO);

    Command getCommand();

}
