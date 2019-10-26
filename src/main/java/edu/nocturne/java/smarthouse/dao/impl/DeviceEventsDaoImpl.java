package edu.nocturne.java.smarthouse.dao.impl;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nocturne.java.smarthouse.dao.DeviceEventsDao;
import edu.nocturne.java.smarthouse.domain.Device;
import edu.nocturne.java.smarthouse.domain.DeviceEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Repository
public class DeviceEventsDaoImpl implements DeviceEventsDao {

    private static final String REFERENCE = "reference";
    private static final String HOUSE_REFERENCE = "houseReference";
    private static final String DEVICE_REFERENCE = "deviceReference";
    private static final String STATE = "deviceState";
    private static final String TIMESTAMP = "eventTimestamp";
    private static final String DEVICE_TYPE = "deviceType";
    private static final String COMMAND = "command";
    private static final String DATA = "data";

    private final Table table;

    @Autowired
    public DeviceEventsDaoImpl(@Qualifier("deviceEvents") Table table) {
        this.table = table;
    }


    @Override
    public void createDevice(DeviceEvent device) {
        device.setEventTimestamp(ZonedDateTime.now(ZoneId.of("UTC")).toEpochSecond());
        Item item = new Item().withPrimaryKey(REFERENCE, UUID.randomUUID().toString())
                              .withNumber(TIMESTAMP, device.getEventTimestamp())
                              .withString(HOUSE_REFERENCE, device.getHouseReference())
                              .withString(DEVICE_REFERENCE, device.getDeviceReference())
                              .withString(STATE, device.getDeviceState().getValue())
                              .withString(DEVICE_TYPE, device.getDeviceType().getValue())
                              .withString(COMMAND, device.getCommand().getValue())
                              .withMap(DATA, device.getData());

        table.putItem(item);
    }

}
