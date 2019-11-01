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


    @Autowired
    public DevicesRestService() {
    }

    @PutMapping("/devices")
    public ResponseEntity<Void> sendAction(@RequestBody DeviceEvent deviceEvent) {
        return ResponseEntity.ok().build();
    }

}
