package edu.nocturne.java.smarthouse.service.business.command;

import edu.nocturne.java.smarthouse.domain.Device;
import edu.nocturne.java.smarthouse.domain.DeviceEvent;

public interface DeviceEventDispatcher {

    void process(DeviceEvent deviceEvent);

}
