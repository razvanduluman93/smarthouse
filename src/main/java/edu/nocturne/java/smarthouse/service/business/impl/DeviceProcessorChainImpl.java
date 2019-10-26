package edu.nocturne.java.smarthouse.service.business.impl;

import edu.nocturne.java.smarthouse.domain.DeviceEvent;
import edu.nocturne.java.smarthouse.service.business.DeviceProcessor;
import edu.nocturne.java.smarthouse.service.business.DeviceProcessorChain;
import edu.nocturne.java.smarthouse.service.mapper.DeviceMapper;
import edu.nocturne.java.smarthouse.type.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class DeviceProcessorChainImpl implements DeviceProcessorChain {

    private static final String UNSUPPORTED_OPERATION = "Unsupported operation";

    private Map<Command, DeviceProcessor> deviceProcessors = new HashMap<>();
    private final DeviceMapper deviceMapper;

    @Autowired
    public DeviceProcessorChainImpl(List<DeviceProcessor> deviceProcessors,
                                    DeviceMapper deviceMapper) {
        deviceProcessors.forEach(processor -> this.deviceProcessors.put(processor.getCommand(), processor));
        this.deviceMapper = deviceMapper;
    }

    @Override
    public void process(DeviceEvent deviceEvent) {
        DeviceProcessor deviceProcessor = Optional.ofNullable(deviceProcessors.get(deviceEvent.getCommand()))
                                                  .orElseThrow(() -> new RuntimeException(UNSUPPORTED_OPERATION));
        deviceProcessor.process(deviceMapper.map(deviceEvent));
    }
}
