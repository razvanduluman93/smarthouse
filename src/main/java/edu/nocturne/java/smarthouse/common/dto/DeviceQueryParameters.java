package edu.nocturne.java.smarthouse.common.dto;

import edu.nocturne.java.smarthouse.common.type.DeviceState;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DeviceQueryParameters {
    private DeviceState deviceState;

}
