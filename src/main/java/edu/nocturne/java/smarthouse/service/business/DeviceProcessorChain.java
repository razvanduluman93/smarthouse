package edu.nocturne.java.smarthouse.service.business;

import edu.nocturne.java.smarthouse.domain.Device;
import edu.nocturne.java.smarthouse.domain.DeviceEvent;

public interface DeviceProcessorChain {

    void process(DeviceEvent deviceEvent);

}
