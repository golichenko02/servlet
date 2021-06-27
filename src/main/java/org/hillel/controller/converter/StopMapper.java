package org.hillel.controller.converter;

import org.hillel.controller.dto.StopDto;
import org.hillel.persistence.entity.StopEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Mapper
public interface StopMapper {


    @Mapping(target = "name", source = "commonInfo.name")
    @Mapping(target = "description", source = "commonInfo.description")
    StopDto stopToDto(StopEntity stop);

    default StopDto fullStopToDto(StopEntity stop) {
        StopDto stopDto = stopToDto(stop);
        stopDto.setLatitude(stop.getAdditionalInfo().getLatitude());
        stopDto.setLongitude(stop.getAdditionalInfo().getLongitude());
        stopDto.setBuildDate(stop.getAdditionalInfo().getBuildDate().toString());
        stopDto.setCity(stop.getAdditionalInfo().getCity());
        return stopDto;
    }

    default String map(Instant value) {
        return value == null ? null : String.valueOf(LocalDateTime.ofInstant(value, ZoneOffset.UTC));
    }

    default String map(boolean active) {
        return active ? "Да" : "Нет";
    }
}
