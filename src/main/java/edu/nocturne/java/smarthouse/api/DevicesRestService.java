package edu.nocturne.java.smarthouse.api;

import edu.nocturne.java.smarthouse.domain.Device;
import edu.nocturne.java.smarthouse.domain.DeviceEvent;
import edu.nocturne.java.smarthouse.service.business.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DevicesRestService {

    private final DeviceService deviceService;

    @Autowired
    public DevicesRestService(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PutMapping("/devices")
    public ResponseEntity<Void> sendAction(@RequestBody DeviceEvent deviceEvent) {
        deviceService.putDevice(deviceEvent);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/devices")
    public ResponseEntity<Device> getDeviceState(@RequestParam("houseReference") String houseReference,
                                                 @RequestParam("deviceReference") String deviceReference) {
        return ResponseEntity.ok(deviceService.getDevice(houseReference, deviceReference));
    }

}
