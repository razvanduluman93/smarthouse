package edu.nocturne.java.smarthouse.api;

import edu.nocturne.java.smarthouse.dto.DeviceEventDTO;
import edu.nocturne.java.smarthouse.service.business.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DevicesRestService {

    private final DeviceService deviceService;

    @Autowired
    public DevicesRestService(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PutMapping("/devices")
    public ResponseEntity<Void> sendAction(@RequestBody DeviceEventDTO deviceEventDTO) {
        deviceService.putDevice(deviceEventDTO);
        return ResponseEntity.ok().build();
    }

}
