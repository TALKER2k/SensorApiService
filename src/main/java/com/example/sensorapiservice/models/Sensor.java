package com.example.sensorapiservice.models;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Accessors(chain = true)
@Table(name = "sensor")
public class Sensor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotEmpty(message = "Sensor name should be not empty")
    @Column(name = "name")
    @Size(min = 3, max = 30, message = "Sensor name should be from 3 to 30 size")
    private String name;

    @NotEmpty(message = "Sensor version should be not empty")
    @Column(name = "version")
    private String version;

    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL)
    private Set<Measurement> measurements;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return "Sensor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
