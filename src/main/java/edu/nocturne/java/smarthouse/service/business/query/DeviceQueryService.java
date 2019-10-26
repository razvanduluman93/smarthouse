package edu.nocturne.java.smarthouse.service.business.query;

import edu.nocturne.java.smarthouse.domain.Device;
import edu.nocturne.java.smarthouse.common.dto.DeviceQueryParameters;

import java.util.List;

public interface DeviceQueryService {


    Device getDevice(String houseReference, String deviceReference);

    List<Device> getFilteredDevices(String houseReference, DeviceQueryParameters queryParameters);
}
