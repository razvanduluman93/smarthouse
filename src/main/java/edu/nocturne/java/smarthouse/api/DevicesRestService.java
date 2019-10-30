package edu.nocturne.java.smarthouse.api;

import edu.nocturne.java.smarthouse.common.dto.DeviceQueryParameters;
import edu.nocturne.java.smarthouse.common.type.DeviceState;
import edu.nocturne.java.smarthouse.domain.Device;
import edu.nocturne.java.smarthouse.domain.DeviceEvent;
import edu.nocturne.java.smarthouse.service.business.query.DeviceQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class DevicesRestService {

    private final DeviceQueryService deviceQueryService;
    private final QueueMessagingTemplate queueMessagingTemplate;

    @Value("${cloud.aws.queues.DeviceEventsQueue}")
    private String queueName;

    @Autowired
    public DevicesRestService(DeviceQueryService deviceQueryService,
                              QueueMessagingTemplate queueMessagingTemplate) {
        this.deviceQueryService = deviceQueryService;
        this.queueMessagingTemplate = queueMessagingTemplate;
    }

    @PutMapping("/devices")
    public ResponseEntity<Void> sendAction(@RequestBody DeviceEvent deviceEvent) {
        queueMessagingTemplate.convertAndSend(queueName, deviceEvent);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/devices/{deviceReference}")
    public ResponseEntity<Device> getDevices(@RequestHeader("houseReference") String houseReference,
                                             @PathVariable("deviceReference") String deviceReference) {
        return ResponseEntity.ok(deviceQueryService.getDevice(houseReference, deviceReference));
    }

    @GetMapping("/devices")
    public ResponseEntity<List<Device>> getDevices(@RequestHeader("houseReference") String houseReference,
                                                   @RequestParam(value = "deviceState", required = false) DeviceState deviceState) {
        DeviceQueryParameters queryParameters = DeviceQueryParameters.builder()
                                                                     .deviceState(deviceState)
                                                                     .build();
        return ResponseEntity.ok(deviceQueryService.getDevices(houseReference, queryParameters));
    }
}
