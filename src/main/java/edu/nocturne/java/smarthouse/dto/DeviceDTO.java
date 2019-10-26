package edu.nocturne.java.smarthouse.dto;

import edu.nocturne.java.smarthouse.type.DeviceState;
import edu.nocturne.java.smarthouse.type.DeviceType;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Map;

@Getter
@Setter
public class DeviceDTO {

    private String houseReference;
    private String deviceReference;
    private ZonedDateTime timestamp;
    private DeviceState state;
    private DeviceType deviceType;
    private Map<String, String> data;
}
