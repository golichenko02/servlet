package org.hillel.controller.converter;

import org.hillel.controller.dto.JourneyDto;
import org.hillel.persistence.entity.JourneyEntity;
import org.mapstruct.Mapper;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Mapper
public interface JourneyMapper {

    JourneyDto journeyToDto(JourneyEntity journey);

    JourneyEntity journeyDtoToJourney(JourneyDto journeyDto);

    default JourneyDto fullJourneyToDto(JourneyEntity journey) {
        JourneyDto journeyDto = journeyToDto(journey);
        journeyDto.setVehicleName(journey.getVehicle().getName());
        journeyDto.setStopsCount(journey.getStops().size());
        return journeyDto;
    }

    default String map(Instant value) {
        return value == null ? null : String.valueOf(LocalDateTime.ofInstant(value, ZoneOffset.UTC));
    }

    default String map(LocalDate value) {
        return value == null ? null : String.valueOf(value);
    }
}
