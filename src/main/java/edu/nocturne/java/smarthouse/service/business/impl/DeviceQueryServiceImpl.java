package edu.nocturne.java.smarthouse.service.business.impl;

import edu.nocturne.java.smarthouse.dao.DeviceDao;
import edu.nocturne.java.smarthouse.dao.DeviceEventsDao;
import edu.nocturne.java.smarthouse.domain.Device;
import edu.nocturne.java.smarthouse.domain.DeviceEvent;
import edu.nocturne.java.smarthouse.service.business.DeviceCommandService;
import edu.nocturne.java.smarthouse.service.business.DeviceProcessorChain;
import edu.nocturne.java.smarthouse.service.business.DeviceQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DeviceQueryServiceImpl implements DeviceQueryService {

    private final DeviceDao deviceDao;

    @Autowired
    public DeviceQueryServiceImpl(DeviceDao deviceDao) {
        this.deviceDao = deviceDao;
    }


    @Override
    public Device getDevice(String houseReference, String deviceReference) {
        return deviceDao.getDevice(houseReference, deviceReference);
    }


}
