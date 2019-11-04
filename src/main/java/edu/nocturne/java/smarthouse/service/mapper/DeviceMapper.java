package edu.nocturne.java.smarthouse.service.mapper;


import edu.nocturne.java.smarthouse.domain.Device;
import edu.nocturne.java.smarthouse.domain.DeviceEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DeviceMapper {

    Device map(DeviceEvent deviceEvent);

    Device map(Device device);

    @Mapping(source = "deviceData", target = "deviceData", ignore = true)
    DeviceEvent replay(DeviceEvent source, @MappingTarget DeviceEvent target);

}
