package edu.nocturne.java.smarthouse.service.listener;

import edu.nocturne.java.smarthouse.domain.DeviceEvent;

public interface DeviceEventListener {
    void process(DeviceEvent deviceEvent);
}
