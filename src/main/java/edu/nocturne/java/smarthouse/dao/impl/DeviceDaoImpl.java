package edu.nocturne.java.smarthouse.dao.impl;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nocturne.java.smarthouse.dao.DeviceDao;
import edu.nocturne.java.smarthouse.domain.Device;
import edu.nocturne.java.smarthouse.common.dto.DeviceQueryParameters;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class DeviceDaoImpl implements DeviceDao {

    private static final String HOUSE_REFERENCE = "houseReference";
    private static final String DEVICE_REFERENCE = "deviceReference";
    private static final String STATE = "deviceState";
    private static final String DEVICE_TYPE = "deviceType";
    private static final String DATA = "data";
    private static final String EQUALS = " = ";
    private static final String HOUSE_REFERENCE_PARAMETER = ":houseReference";
    private static final String STATE_PARAMETER = ":deviceState";

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
        Iterator<Item> iterator = table.query(querySpec).iterator();
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
