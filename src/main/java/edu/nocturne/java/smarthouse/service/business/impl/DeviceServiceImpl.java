package edu.nocturne.java.smarthouse.service.business.impl;

import edu.nocturne.java.smarthouse.dao.DeviceDao;
import edu.nocturne.java.smarthouse.dao.DeviceEventsDao;
import edu.nocturne.java.smarthouse.domain.Device;
import edu.nocturne.java.smarthouse.domain.DeviceEvent;
import edu.nocturne.java.smarthouse.service.business.DeviceProcessorChain;
import edu.nocturne.java.smarthouse.service.business.DeviceService;
import edu.nocturne.java.smarthouse.service.mapper.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceEventsDao deviceEventsDao;
    private final DeviceDao deviceDao;
    private final DeviceProcessorChain deviceProcessorChain;

    @Autowired
    public DeviceServiceImpl(DeviceEventsDao deviceEventsDao,
                             DeviceDao deviceDao,
                             DeviceProcessorChain deviceProcessorChain) {
        this.deviceEventsDao = deviceEventsDao;
        this.deviceDao = deviceDao;
        this.deviceProcessorChain = deviceProcessorChain;
    }

    @Override
    public void putDevice(DeviceEvent deviceEvent) {
        deviceEventsDao.createDevice(deviceEvent);
        deviceProcessorChain.process(deviceEvent);
    }

    @Override
    public Device getDevice(String houseReference, String deviceReference) {
        return deviceDao.getDevice(houseReference, deviceReference);
    }


}
