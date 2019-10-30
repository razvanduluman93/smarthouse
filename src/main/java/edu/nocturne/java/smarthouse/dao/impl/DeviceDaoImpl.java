package edu.nocturne.java.smarthouse.dao.impl;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nocturne.java.smarthouse.common.dto.DeviceQueryParameters;
import edu.nocturne.java.smarthouse.dao.DeviceDao;
import edu.nocturne.java.smarthouse.domain.Device;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static edu.nocturne.java.smarthouse.common.constant.TableColumnConstants.*;
import static edu.nocturne.java.smarthouse.common.constant.TableFilterConstants.*;

@Repository
public class DeviceDaoImpl implements DeviceDao {

    private final Table table;
    private final ObjectMapper objectMapper;

    public DeviceDaoImpl(@Qualifier("devices") Table table,
                         ObjectMapper objectMapper) {
        this.table = table;
        this.objectMapper = objectMapper;
    }


    @Override
    public void createDevice(Device device) {
        Item item = new Item().withPrimaryKey(HOUSE_REFERENCE, device.getHouseReference(),
                                              DEVICE_REFERENCE, device.getDeviceReference())
                              .withString(STATE, device.getDeviceState().getValue())
                              .withString(DEVICE_TYPE, device.getDeviceType().getValue())
                              .withMap(DATA, device.getData());

        table.putItem(item);
    }

    @Override
    public Device getDevice(String houseReference, String deviceReference) {
        PrimaryKey primaryKey = new PrimaryKey(HOUSE_REFERENCE, houseReference, DEVICE_REFERENCE, deviceReference);
        Item item = table.getItem(primaryKey);
        try {
            return objectMapper.readValue(item.toJSON(), Device.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Device> getFilteredDevice(String houseReference, DeviceQueryParameters queryParameters) {
        QuerySpec querySpec = new QuerySpec()
                .withKeyConditionExpression(HOUSE_REFERENCE + EQUALS + HOUSE_REFERENCE_PARAMETER)
                .withFilterExpression(STATE + EQUALS + STATE_PARAMETER)
                .withValueMap(new ValueMap().withString(HOUSE_REFERENCE_PARAMETER, houseReference)
                                            .withString(STATE_PARAMETER, queryParameters.getDeviceState().getValue()));
        return getDevices(table.query(querySpec));
    }

    @Override
    public List<Device> getDevices(String houseReference) {
        QuerySpec querySpec = new QuerySpec()
                .withKeyConditionExpression(HOUSE_REFERENCE + EQUALS + HOUSE_REFERENCE_PARAMETER)
                .withValueMap(new ValueMap().withString(HOUSE_REFERENCE_PARAMETER, houseReference));
        return getDevices(table.query(querySpec));
    }

    private List<Device> getDevices(ItemCollection<QueryOutcome> queryOutcome) {
        Iterator<Item> iterator = queryOutcome.iterator();
        List<Device> items = new ArrayList<>();
        while (iterator.hasNext()) {
            try {
                items.add(objectMapper.readValue(iterator.next().toJSON(), Device.class));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return items;
    }
}
