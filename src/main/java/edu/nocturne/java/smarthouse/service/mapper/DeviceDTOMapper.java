package edu.nocturne.java.smarthouse.service.mapper;


import edu.nocturne.java.smarthouse.domain.DeviceEvent;
import edu.nocturne.java.smarthouse.dto.DeviceEventDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DeviceDTOMapper {

    @Mapping(ignore = true, target = "reference")
    DeviceEvent map(DeviceEventDTO deviceEventDTO);

}
