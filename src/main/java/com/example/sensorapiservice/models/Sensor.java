package com.example.sensorapiservice.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
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

    public Sensor() {}

    public Sensor(String name, String version, Set<Measurement> measurements, LocalDateTime createdAt) {
        this.name = name;
        this.version = version;
        this.measurements = measurements;
        this.createdAt = createdAt;
    }

    public Set<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(Set<Measurement> measurements) {
        this.measurements = measurements;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

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
