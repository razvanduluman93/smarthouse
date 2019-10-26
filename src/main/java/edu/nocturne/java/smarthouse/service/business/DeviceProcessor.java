package edu.nocturne.java.smarthouse.service.business;

import edu.nocturne.java.smarthouse.dto.DeviceEventDTO;
import edu.nocturne.java.smarthouse.type.Command;

public interface DeviceProcessor {

    void process(DeviceEventDTO deviceEventDTO);
    Command getCommand();

}
