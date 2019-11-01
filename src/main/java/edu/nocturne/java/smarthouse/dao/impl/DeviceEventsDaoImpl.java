package edu.nocturne.java.smarthouse.dao.impl;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nocturne.java.smarthouse.common.type.Command;
import edu.nocturne.java.smarthouse.dao.DeviceEventsDao;
import edu.nocturne.java.smarthouse.domain.DeviceEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static edu.nocturne.java.smarthouse.common.constant.TableColumnConstants.*;
import static edu.nocturne.java.smarthouse.common.constant.TableFilterConstants.*;

@Repository
public class DeviceEventsDaoImpl implements DeviceEventsDao {

    private final Table table;
    private final ObjectMapper objectMapper;

    @Value("${cloud.aws.dynamodb.tables.DeviceEvents.indexes.DeviceEventsHouseDeviceIndex}")
    private String houseDeviceIndex;

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
                              .withString(STATE, device.getDeviceState().toString())
                              .withString(DEVICE_TYPE, device.getDeviceType().toString())
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
        Iterator<Item> iterator = table.getIndex(houseDeviceIndex)
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
