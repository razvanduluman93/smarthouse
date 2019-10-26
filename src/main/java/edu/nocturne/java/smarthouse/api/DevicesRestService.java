package edu.nocturne.java.smarthouse.api;

import edu.nocturne.java.smarthouse.domain.Device;
import edu.nocturne.java.smarthouse.domain.DeviceEvent;
import edu.nocturne.java.smarthouse.service.business.DeviceCommandService;
import edu.nocturne.java.smarthouse.service.business.DeviceQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DevicesRestService {

    private final DeviceCommandService deviceCommandService;
    private final DeviceQueryService deviceQueryService;

    @Autowired
    public DevicesRestService(DeviceCommandService deviceCommandService,
                              DeviceQueryService deviceQueryService) {
        this.deviceCommandService = deviceCommandService;
        this.deviceQueryService = deviceQueryService;
    }

    @PutMapping("/devices")
    public ResponseEntity<Void> sendAction(@RequestBody DeviceEvent deviceEvent) {
        deviceCommandService.putDevice(deviceEvent);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/devices")
    public ResponseEntity<Device> getDeviceState(@RequestParam("houseReference") String houseReference,
                                                 @RequestParam("deviceReference") String deviceReference) {
        return ResponseEntity.ok(deviceQueryService.getDevice(houseReference, deviceReference));
    }

}
