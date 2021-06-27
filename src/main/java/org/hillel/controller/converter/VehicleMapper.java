package org.hillel.controller.converter;

import org.hillel.controller.dto.VehicleDto;
import org.hillel.persistence.entity.VehicleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Mapper
public interface VehicleMapper {


    VehicleDto vehicleToVehicleDto(VehicleEntity vehicle);

    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "active", ignore = true)
    VehicleEntity vehicleDtoToVehicle(VehicleDto vehicleDto);

    default String map(Instant value) {
        return value == null ? null : String.valueOf(LocalDateTime.ofInstant(value, ZoneOffset.UTC));
    }

    default String map(boolean active) {
        return active ? "Да" : "Нет";
    }
}
