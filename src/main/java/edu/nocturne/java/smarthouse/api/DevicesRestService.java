package edu.nocturne.java.smarthouse.api;

import edu.nocturne.java.smarthouse.dto.DeviceDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DevicesRestService {

    @PutMapping("/devices")
    public ResponseEntity<DeviceDTO> sendAction(@RequestBody DeviceDTO deviceDTO){
        return ResponseEntity.ok(deviceDTO);
    }

}
