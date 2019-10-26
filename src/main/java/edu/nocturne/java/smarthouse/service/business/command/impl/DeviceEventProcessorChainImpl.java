package edu.nocturne.java.smarthouse.service.business.command.impl;

import edu.nocturne.java.smarthouse.domain.DeviceEvent;
import edu.nocturne.java.smarthouse.service.business.command.DeviceEventProcessor;
import edu.nocturne.java.smarthouse.service.business.command.DeviceEventProcessorChain;
import edu.nocturne.java.smarthouse.common.type.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class DeviceEventProcessorChainImpl implements DeviceEventProcessorChain {

    private static final String UNSUPPORTED_OPERATION = "Unsupported operation";

    private Map<Command, DeviceEventProcessor> deviceEventProcessors = new HashMap<>();

    @Autowired
    public DeviceEventProcessorChainImpl(List<DeviceEventProcessor> deviceEventProcessors) {
        deviceEventProcessors.forEach(processor -> this.deviceEventProcessors.put(processor.getCommand(), processor));
    }

    @Override
    public void process(DeviceEvent deviceEvent) {
        DeviceEventProcessor deviceEventProcessor = Optional.ofNullable(deviceEventProcessors.get(deviceEvent.getCommand()))
                                                            .orElseThrow(() -> new RuntimeException(UNSUPPORTED_OPERATION));
        deviceEventProcessor.process(deviceEvent);
    }
}
