package edu.nocturne.java.smarthouse.service.mapper;


import edu.nocturne.java.smarthouse.domain.Device;
import edu.nocturne.java.smarthouse.domain.DeviceEvent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeviceMapper {

    Device map(DeviceEvent deviceEvent);
    Device map(Device device);

}
