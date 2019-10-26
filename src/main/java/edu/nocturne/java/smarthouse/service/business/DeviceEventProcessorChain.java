package edu.nocturne.java.smarthouse.service.business;

import edu.nocturne.java.smarthouse.domain.Device;
import edu.nocturne.java.smarthouse.domain.DeviceEvent;

public interface DeviceEventProcessorChain {

    void process(DeviceEvent deviceEvent);

}
