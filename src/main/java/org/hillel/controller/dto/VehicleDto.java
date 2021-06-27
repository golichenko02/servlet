package org.hillel.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleDto {
    private long id;

    private String name;

    private int maxSeats;

    private String createDate;

    private String active;
}
