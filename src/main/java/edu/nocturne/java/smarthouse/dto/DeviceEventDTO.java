package edu.nocturne.java.smarthouse.dto;

import edu.nocturne.java.smarthouse.type.Command;
import edu.nocturne.java.smarthouse.type.DeviceState;
import edu.nocturne.java.smarthouse.type.DeviceType;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Map;

@Getter
@Setter
public class DeviceEventDTO {

    private String reference;
    private String houseReference;
    private String deviceReference;
    private ZonedDateTime timestamp;
    private DeviceState state;
    private DeviceType deviceType;
    private Command command;
    private Map<String, String> data;
}
