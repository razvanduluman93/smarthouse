package edu.nocturne.java.smarthouse.dao.impl;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import edu.nocturne.java.smarthouse.dao.DeviceDao;
import edu.nocturne.java.smarthouse.domain.Device;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class DeviceDaoImpl implements DeviceDao {

    private final Table table;

    public DeviceDaoImpl(@Qualifier("devices") Table table) {
        this.table = table;
    }


    @Override
    public void createDevice(Device device) {
        Item item = new Item()
                .withPrimaryKey("house_reference", device.getHouseReference(),
                                "device_reference", device.getDeviceReference())
                .withString("state", device.getState().toString())
                .withString("timestamp", device.getTimestamp().toString())
                .withString("device_type", device.getDeviceType().toString())
                .withMap("data", device.getData());

        table.putItem(item);
    }
}
