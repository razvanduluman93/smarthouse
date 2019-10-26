package edu.nocturne.java.smarthouse.service.business.impl;

import edu.nocturne.java.smarthouse.dto.DeviceEventDTO;
import edu.nocturne.java.smarthouse.service.business.DeviceProcessor;
import edu.nocturne.java.smarthouse.service.business.DeviceProcessorChain;
import edu.nocturne.java.smarthouse.type.Command;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class DeviceProcessorChainImpl implements DeviceProcessorChain {

    Map<Command, DeviceProcessor> deviceProcessors;

    @Autowired
    public DeviceProcessorChainImpl(List<DeviceProcessor> deviceProcessors) {
        deviceProcessors.forEach(processor -> this.deviceProcessors.put(processor.getCommand(), processor));
    }

    @Override
    public void process(DeviceEventDTO deviceEventDTO) {
        deviceProcessors.get(deviceEventDTO.getCommand()).process(deviceEventDTO);
    }
}
