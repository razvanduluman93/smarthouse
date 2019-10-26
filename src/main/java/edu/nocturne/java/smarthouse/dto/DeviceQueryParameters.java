package edu.nocturne.java.smarthouse.dto;

import edu.nocturne.java.smarthouse.type.DeviceState;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DeviceQueryParameters {
    private DeviceState deviceState;

}
