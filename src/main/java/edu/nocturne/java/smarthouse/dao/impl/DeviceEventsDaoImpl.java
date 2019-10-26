package edu.nocturne.java.smarthouse.dao.impl;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import edu.nocturne.java.smarthouse.dao.DeviceEventsDao;
import edu.nocturne.java.smarthouse.domain.DeviceEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class DeviceEventsDaoImpl implements DeviceEventsDao {

    private final Table table;

    @Autowired
    public DeviceEventsDaoImpl(@Qualifier("deviceEvents") Table table) {
        this.table = table;
    }


    @Override
    public void createDevice(DeviceEvent device) {
        Item item = new Item().withPrimaryKey("reference", UUID.randomUUID().toString())
                              .withString("house_reference", device.getHouseReference())
                              .withString("device_reference", device.getDeviceReference())
                              .withString("state", device.getState().toString())
                              .withString("timestamp", device.getTimestamp().toString())
                              .withString("device_type", device.getDeviceType().toString())
                              .withString("command", device.getCommand().toString())
                              .withMap("data", device.getData());

        table.putItem(item);
    }
}
