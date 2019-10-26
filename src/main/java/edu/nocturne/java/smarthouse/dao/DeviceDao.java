package edu.nocturne.java.smarthouse.dao;

import edu.nocturne.java.smarthouse.domain.Device;

public interface DeviceDao {

    void createDevice(Device device);

    Device getDevice(String houseReference, String deviceReference);
}
