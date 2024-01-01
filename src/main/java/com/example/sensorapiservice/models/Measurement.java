package com.example.sensorapiservice.models;


import com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Data
@Accessors(chain = true)
@Table(name = "measurement")
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Min(value = -100)
    @Max(value = 100)
    @Column(name = "value")
    private Double value;

    @Column(name = "raining")
    @NotNull
    private Boolean raining;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_id", referencedColumnName = "name")
    private Sensor sensor;


    @Override
    public String toString() {
        return "Measurement{" +
                "id=" + id +
                ", value=" + value +
                ", raining=" + raining +
                ", sensor=" + sensor +
                '}';
    }
}
