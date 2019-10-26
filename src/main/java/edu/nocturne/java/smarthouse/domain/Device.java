package edu.nocturne.java.smarthouse.domain;

import edu.nocturne.java.smarthouse.common.type.DeviceState;
import edu.nocturne.java.smarthouse.common.type.DeviceType;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Device {

    private String houseReference;
    private String deviceReference;
    private DeviceState deviceState;
    private DeviceType deviceType;
    private Map<String, String> data;

}
