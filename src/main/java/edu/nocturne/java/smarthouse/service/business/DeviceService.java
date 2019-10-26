package edu.nocturne.java.smarthouse.service.business;

import edu.nocturne.java.smarthouse.domain.Device;
import edu.nocturne.java.smarthouse.domain.DeviceEvent;

public interface DeviceService {

    void putDevice(DeviceEvent deviceEvent);

    Device getDevice(String houseReference, String deviceReference);

}
