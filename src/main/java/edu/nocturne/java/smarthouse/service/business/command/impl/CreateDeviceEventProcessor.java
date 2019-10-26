package edu.nocturne.java.smarthouse.service.business.command.impl;

import edu.nocturne.java.smarthouse.dao.DeviceEventsDao;
import edu.nocturne.java.smarthouse.domain.DeviceEvent;
import edu.nocturne.java.smarthouse.service.business.command.DeviceEventProcessor;
import edu.nocturne.java.smarthouse.service.business.query.DeviceProcessor;
import edu.nocturne.java.smarthouse.service.mapper.DeviceMapper;
import edu.nocturne.java.smarthouse.type.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateDeviceEventProcessor implements DeviceEventProcessor {

    private final DeviceEventsDao deviceEventsDao;
    private final DeviceProcessor deviceProcessor;
    private final DeviceMapper deviceMapper;

    @Autowired
    public CreateDeviceEventProcessor(DeviceEventsDao deviceEventsDao,
                                      DeviceProcessor deviceProcessor,
                                      DeviceMapper deviceMapper) {
        this.deviceEventsDao = deviceEventsDao;
        this.deviceProcessor = deviceProcessor;
        this.deviceMapper = deviceMapper;
    }

    @Override
    public void process(DeviceEvent deviceEvent) {
        if (deviceEventsDao.getDeviceEvents(deviceEvent.getHouseReference(), deviceEvent.getDeviceReference()).isEmpty()) {
            deviceEventsDao.createDeviceEvent(deviceEvent);
            deviceProcessor.process(deviceMapper.map(deviceEvent));
        }
    }

    @Override
    public Command getCommand() {
        return Command.CREATE;
    }
}
