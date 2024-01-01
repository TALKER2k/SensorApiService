package com.example.sensorapiservice.DTO;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class SensorDTO {

    @NotEmpty(message = "Sensor name should be not empty")
    @Size(min = 3, max = 30, message = "Sensor name should be from 3 to 30 size")
    private String name;
}
