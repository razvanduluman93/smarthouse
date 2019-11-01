package edu.nocturne.java.smarthouse.service.business.query.impl;

import edu.nocturne.java.smarthouse.dao.DeviceDao;
import edu.nocturne.java.smarthouse.domain.Device;
import edu.nocturne.java.smarthouse.common.dto.DeviceQueryParameters;
import edu.nocturne.java.smarthouse.service.business.query.DeviceQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DeviceQueryServiceImpl implements DeviceQueryService {

    private final DeviceDao deviceDao;

    @Autowired
    public DeviceQueryServiceImpl(DeviceDao deviceDao) {
        this.deviceDao = deviceDao;
    }


    @Override
    public Device getDevice(String houseReference, String deviceReference) {
        return null;
    }


    @Override
    public List<Device> getDevices(String houseReference, DeviceQueryParameters queryParameters) {
        return deviceDao.getDevices(houseReference);

    }


}
