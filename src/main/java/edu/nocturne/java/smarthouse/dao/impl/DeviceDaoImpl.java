package edu.nocturne.java.smarthouse.dao.impl;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.KeyAttribute;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nocturne.java.smarthouse.common.dto.DeviceQueryParameters;
import edu.nocturne.java.smarthouse.dao.DeviceDao;
import edu.nocturne.java.smarthouse.domain.Device;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

import static edu.nocturne.java.smarthouse.common.constant.TableColumnConstants.*;
import static edu.nocturne.java.smarthouse.common.constant.TableFilterConstants.*;

@Repository
public class DeviceDaoImpl implements DeviceDao {

    private final Table table;

    @Value("${cloud.aws.dynamodb.tables.Devices.name}")
    private String devicesTable;

    public DeviceDaoImpl(@Qualifier("devices") Table table) {
        this.table = table;
    }


    @Override
    public void putDevice(Device device) {
        Item item = new Item().withPrimaryKey(HOUSE_REFERENCE, device.getHouseReference(),
                                              DEVICE_REFERENCE, device.getDeviceReference())
                              .withString(STATE, device.getDeviceState().getValue())
                              .withString(DEVICE_TYPE, device.getDeviceType().getValue())
                              .withMap(DATA, device.getDeviceData());

        table.putItem(item);
    }

    @Override
    public void updateDevice(Device device) {
        KeyAttribute partitionKey = new KeyAttribute(HOUSE_REFERENCE, device.getHouseReference());
        KeyAttribute sortKey = new KeyAttribute(DEVICE_REFERENCE, device.getDeviceReference());
        UpdateItemSpec updateItem = new UpdateItemSpec()
                .withPrimaryKey(partitionKey, sortKey)
                .withUpdateExpression("set " + STATE + EQUALS + STATE_PARAMETER +
                                              ", " + DATA + EQUALS + DATA_PARAMETER +
                                              ", " + DEVICE_TYPE + EQUALS + DEVICE_TYPE_PARAMETER)
                .withValueMap(new ValueMap().withString(STATE_PARAMETER, device.getDeviceState().getValue())
                                            .withMap(DATA_PARAMETER, device.getDeviceData())
                                            .withString(DEVICE_TYPE_PARAMETER, device.getDeviceType().getValue()))
                .withReturnValues(ReturnValue.ALL_NEW);

        table.updateItem(updateItem);
    }

    @Override
    public Device getDevice(String houseReference, String deviceReference) {
        return null;
    }

    @Override
    public List<Device> getFilteredDevice(String houseReference, DeviceQueryParameters queryParameters) {
        return null;
    }

    @Override
    public List<Device> getDevices(String houseReference) {
        return null;
    }

}
