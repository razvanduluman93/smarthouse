package edu.nocturne.java.smarthouse.service.business.impl;

import edu.nocturne.java.smarthouse.dao.DeviceEventsDao;
import edu.nocturne.java.smarthouse.domain.DeviceEvent;
import edu.nocturne.java.smarthouse.dto.DeviceEventDTO;
import edu.nocturne.java.smarthouse.service.business.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceEventsDao deviceEventsDao;

    @Autowired
    public DeviceServiceImpl(DeviceEventsDao deviceEventsDao) {
        this.deviceEventsDao = deviceEventsDao;
    }

    @Override
    public void putDevice(DeviceEventDTO deviceEventDTO) {
        DeviceEvent device = new DeviceEvent();
        device.setHouseReference(deviceEventDTO.getHouseReference());
        deviceEventsDao.createDevice(device);
    }


}
