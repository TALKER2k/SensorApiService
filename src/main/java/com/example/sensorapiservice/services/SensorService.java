package com.example.sensorapiservice.services;

import com.example.sensorapiservice.DTO.SensorDTO;
import com.example.sensorapiservice.models.Sensor;
import com.example.sensorapiservice.repositories.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class SensorService {
    private final SensorRepository sensorRepository;

    @Transactional
    public void save(SensorDTO sensor) {
        Sensor sensorForSave = new Sensor()
                .setName(sensor.getName())
                .setVersion("1.0.1")
                .setCreatedAt(LocalDateTime.now())
                .setMeasurements(null);
        sensorRepository.save(sensorForSave);
    }

    public List<Sensor> findAll() {
        return sensorRepository.findAll();
    }

    public Sensor findByName(String name) {
        return sensorRepository.findByName(name).orElse(null);
    }
}
