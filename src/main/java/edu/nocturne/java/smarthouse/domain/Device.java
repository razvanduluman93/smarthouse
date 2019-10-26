package edu.nocturne.java.smarthouse.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import edu.nocturne.java.smarthouse.type.DeviceState;
import edu.nocturne.java.smarthouse.type.DeviceType;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Device {

    private String houseReference;
    private String deviceReference;
    @DynamoDBTypeConvertedEnum
    private DeviceState state;
    @DynamoDBTypeConvertedEnum
    private DeviceType deviceType;
    private Map<String, String> data;

}
