package edu.nocturne.java.smarthouse.dao.impl;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nocturne.java.smarthouse.dao.DeviceDao;
import edu.nocturne.java.smarthouse.domain.Device;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class DeviceDaoImpl implements DeviceDao {

    private static final String HOUSE_REFERENCE = "houseReference";
    private static final String DEVICE_REFERENCE = "deviceReference";
    private static final String STATE = "state";
    private static final String DEVICE_TYPE = "deviceType";
    private static final String DATA = "data";

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
                              .withString(STATE, device.getState().getValue())
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
}
