package edu.nocturne.java.smarthouse.dao;

import edu.nocturne.java.smarthouse.domain.DeviceEvent;

import java.time.ZonedDateTime;
import java.util.List;

public interface DeviceEventsDao {

    void createDeviceEvent(DeviceEvent device);

    List<DeviceEvent> getDeviceEvents(String houseReference, String deviceReference);

    List<DeviceEvent> getDevices(String houseReference, ZonedDateTime timestamp);
}
