package edu.nocturne.java.smarthouse.domain;

import edu.nocturne.java.smarthouse.type.DeviceState;
import edu.nocturne.java.smarthouse.type.DeviceType;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Map;

@Getter
@Setter
public class Device {

    private String houseReference;
    private String deviceReference;
    private ZonedDateTime timestamp;
    private DeviceState state;
    private DeviceType deviceType;
    private Map<String, String> data;

}
