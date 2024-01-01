package com.example.sensorapiservice.DTO;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class MeasurementDTO {
    @NotNull
    @Min(value = -100)
    @Max(value = 100)
    private Double value;

    @NotNull
    private Boolean raining;

    @NotNull
    private SensorDTO sensor;
}
