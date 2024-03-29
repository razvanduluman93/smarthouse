package edu.nocturne.java.smarthouse.domain;

import edu.nocturne.java.smarthouse.common.type.Command;
import edu.nocturne.java.smarthouse.common.type.DeviceState;
import edu.nocturne.java.smarthouse.common.type.DeviceType;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class DeviceEvent {

    private String reference;
    private String houseReference;
    private String deviceReference;
    private Long eventTimestamp;
    private DeviceState deviceState;
    private DeviceType deviceType;
    private Command command;
    private Map<String, String> deviceData = new HashMap<>();

}
