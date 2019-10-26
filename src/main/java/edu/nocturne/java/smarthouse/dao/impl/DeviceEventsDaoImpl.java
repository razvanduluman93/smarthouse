package edu.nocturne.java.smarthouse.dao.impl;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nocturne.java.smarthouse.dao.DeviceEventsDao;
import edu.nocturne.java.smarthouse.domain.DeviceEvent;
import edu.nocturne.java.smarthouse.type.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

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
    private static final String EQUALS = " = ";
    private static final String HOUSE_REFERENCE_PARAMETER = ":houseReference";
    private static final String COMMAND_PARAMETER = ":command";
    private static final String DEVICE_REFERENCE_PARAMETER = ":deviceReference";
    private static final String AND = " and ";

    private final Table table;
    private final ObjectMapper objectMapper;

    @Autowired
    public DeviceEventsDaoImpl(@Qualifier("deviceEvents") Table table,
                               ObjectMapper objectMapper) {
        this.table = table;
        this.objectMapper = objectMapper;
    }


    @Override
    public void createDeviceEvent(DeviceEvent device) {
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

    @Override
    public List<DeviceEvent> getDeviceEvents(String houseReference, String deviceReference) {
        QuerySpec querySpec = new QuerySpec()
                .withKeyConditionExpression(HOUSE_REFERENCE + EQUALS + HOUSE_REFERENCE_PARAMETER + AND +
                                                    DEVICE_REFERENCE + EQUALS + DEVICE_REFERENCE_PARAMETER)
                .withFilterExpression(COMMAND + EQUALS + COMMAND_PARAMETER)
                .withValueMap(new ValueMap().withString(HOUSE_REFERENCE_PARAMETER, houseReference)
                                            .withString(DEVICE_REFERENCE_PARAMETER, deviceReference)
                                            .withString(COMMAND_PARAMETER, Command.CREATE.getValue()));
        Iterator<Item> iterator = table.getIndex("houseReference-deviceReference-index")
                                       .query(querySpec)
                                       .iterator();
        List<DeviceEvent> items = new ArrayList<>();
        while (iterator.hasNext()) {
            try {
                items.add(objectMapper.readValue(iterator.next().toJSON(), DeviceEvent.class));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return items;
    }

}
