package edu.nocturne.java.smarthouse.dao;

import edu.nocturne.java.smarthouse.domain.Device;
import edu.nocturne.java.smarthouse.common.dto.DeviceQueryParameters;

import java.util.List;

public interface DeviceDao {

    void createDevice(Device device);

    Device getDevice(String houseReference, String deviceReference);

    List<Device> getFilteredDevice(String houseReference, DeviceQueryParameters deviceQueryParameters);
}
