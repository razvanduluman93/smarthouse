package edu.nocturne.java.smarthouse.service.business;

import edu.nocturne.java.smarthouse.dto.DeviceDTO;
import edu.nocturne.java.smarthouse.dto.DeviceEventDTO;

public interface DeviceProcessorChain {

    void process(DeviceEventDTO deviceEventDTO);

}
