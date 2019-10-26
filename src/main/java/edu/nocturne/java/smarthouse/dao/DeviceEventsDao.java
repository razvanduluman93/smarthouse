package edu.nocturne.java.smarthouse.dao;

import edu.nocturne.java.smarthouse.domain.DeviceEvent;

import java.util.List;

public interface DeviceEventsDao {

    void createDevice(DeviceEvent device);

    List<DeviceEvent> getDeviceEvents(String houseReference, String deviceReference);
}
