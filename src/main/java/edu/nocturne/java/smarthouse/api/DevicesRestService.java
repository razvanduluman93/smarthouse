package edu.nocturne.java.smarthouse.api;

import edu.nocturne.java.smarthouse.domain.Device;
import edu.nocturne.java.smarthouse.domain.DeviceEvent;
import edu.nocturne.java.smarthouse.common.dto.DeviceQueryParameters;
import edu.nocturne.java.smarthouse.service.business.command.DeviceCommandService;
import edu.nocturne.java.smarthouse.service.business.query.DeviceQueryService;
import edu.nocturne.java.smarthouse.common.type.DeviceState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/devices/{deviceReference}")
    public ResponseEntity<Device> getDevices(@RequestHeader("houseReference") String houseReference,
                                             @PathVariable("deviceReference") String deviceReference) {
        return ResponseEntity.ok(deviceQueryService.getDevice(houseReference, deviceReference));
    }

    @GetMapping("/devices")
    public ResponseEntity<List<Device>> getDevices(@RequestHeader("houseReference") String houseReference,
                                                   @RequestParam(value = "deviceState") DeviceState deviceState) {
        DeviceQueryParameters queryParameters = DeviceQueryParameters.builder()
                                                                     .deviceState(deviceState)
                                                                     .build();
        return ResponseEntity.ok(deviceQueryService.getFilteredDevices(houseReference, queryParameters));
    }
}
