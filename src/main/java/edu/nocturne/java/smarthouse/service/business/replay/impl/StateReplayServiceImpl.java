package edu.nocturne.java.smarthouse.service.business.replay.impl;

import edu.nocturne.java.smarthouse.dao.DeviceEventsDao;
import edu.nocturne.java.smarthouse.domain.Device;
import edu.nocturne.java.smarthouse.domain.DeviceEvent;
import edu.nocturne.java.smarthouse.service.business.replay.StateReplayService;
import edu.nocturne.java.smarthouse.service.mapper.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StateReplayServiceImpl implements StateReplayService {

    private final DeviceMapper deviceMapper;
    private final DeviceEventsDao deviceEventsDao;

    @Autowired
    public StateReplayServiceImpl(DeviceMapper deviceMapper,
                                  DeviceEventsDao deviceEventsDao) {
        this.deviceMapper = deviceMapper;
        this.deviceEventsDao = deviceEventsDao;
    }

    @Override
    public List<Device> getStateAtTime(String houseReference, ZonedDateTime timestamp) {
        List<DeviceEvent> houseEvents = deviceEventsDao.getDevices(houseReference, timestamp);
        Map<String, List<DeviceEvent>> deviceEvents = houseEvents.stream()
                                                                 .collect(Collectors.groupingBy(DeviceEvent::getDeviceReference));
        return deviceEvents.keySet()
                           .stream()
                           .map(deviceReference -> aggregatedState(deviceEvents.get(deviceReference)))
                           .map(deviceMapper::map)
                           .collect(Collectors.toList());
    }

    private DeviceEvent aggregatedState(List<DeviceEvent> deviceEvents) {
        DeviceEvent deviceEvent = new DeviceEvent();
        List<DeviceEvent> sortedEvents = deviceEvents.stream()
                                                     .sorted(Comparator.comparing(DeviceEvent::getEventTimestamp))
                                                     .collect(Collectors.toList());
        for (DeviceEvent event : sortedEvents) {
            deviceEvent = deviceMapper.replay(event, deviceEvent);
            mapData(deviceEvent, event);
        }
        return deviceEvent;
    }

    private void mapData(DeviceEvent deviceEvent, DeviceEvent event) {
        for (String key : event.getDeviceData().keySet()) {
            deviceEvent.getDeviceData().put(key, event.getDeviceData().get(key));
        }
    }
}
