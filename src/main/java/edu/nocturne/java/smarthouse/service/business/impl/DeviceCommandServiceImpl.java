package edu.nocturne.java.smarthouse.service.business.impl;

import edu.nocturne.java.smarthouse.dao.DeviceDao;
import edu.nocturne.java.smarthouse.dao.DeviceEventsDao;
import edu.nocturne.java.smarthouse.domain.Device;
import edu.nocturne.java.smarthouse.domain.DeviceEvent;
import edu.nocturne.java.smarthouse.service.business.DeviceProcessorChain;
import edu.nocturne.java.smarthouse.service.business.DeviceCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DeviceCommandServiceImpl implements DeviceCommandService {

    private final DeviceEventsDao deviceEventsDao;
    private final DeviceProcessorChain deviceProcessorChain;

    @Autowired
    public DeviceCommandServiceImpl(DeviceEventsDao deviceEventsDao,
                                    DeviceProcessorChain deviceProcessorChain) {
        this.deviceEventsDao = deviceEventsDao;
        this.deviceProcessorChain = deviceProcessorChain;
    }

    @Override
    public void putDevice(DeviceEvent deviceEvent) {
        deviceEventsDao.createDevice(deviceEvent);
        deviceProcessorChain.process(deviceEvent);
    }

}
