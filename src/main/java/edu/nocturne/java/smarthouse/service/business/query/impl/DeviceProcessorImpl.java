package edu.nocturne.java.smarthouse.service.business.query.impl;

import edu.nocturne.java.smarthouse.dao.DeviceDao;
import edu.nocturne.java.smarthouse.domain.Device;
import edu.nocturne.java.smarthouse.service.business.query.DeviceProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeviceProcessorImpl implements DeviceProcessor {

    private final DeviceDao deviceDao;

    @Autowired
    public DeviceProcessorImpl(DeviceDao deviceDao) {
        this.deviceDao = deviceDao;
    }

    @Override
    public void process(Device device) {
        deviceDao.createDevice(device);
    }

}