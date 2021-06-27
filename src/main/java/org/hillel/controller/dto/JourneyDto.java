package org.hillel.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JourneyDto {

    private long id;
    private String stationFrom;
    private String stationTo;
    private String departure;
    private String arrival;
    private String createDate;

    private String vehicleName;
    private int stopsCount;
}
