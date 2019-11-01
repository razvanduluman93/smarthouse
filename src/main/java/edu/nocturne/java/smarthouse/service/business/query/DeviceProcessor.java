package edu.nocturne.java.smarthouse.service.business.query;

import edu.nocturne.java.smarthouse.domain.Device;

public interface DeviceProcessor {

    void create(Device deviceEventDTO);
    void update(Device deviceEventDTO);
}
