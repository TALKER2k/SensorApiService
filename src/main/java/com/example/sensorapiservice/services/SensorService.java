package com.example.sensorapiservice.services;

import com.example.sensorapiservice.models.Sensor;
import com.example.sensorapiservice.repositories.SensorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class SensorService {
    private final SensorRepository sensorRepository;

    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Transactional
    public void save(Sensor sensor) {
        enrichPerson(sensor);
        sensorRepository.save(sensor);
    }

    private void enrichPerson(Sensor sensor) {
        sensor.setCreatedAt(LocalDateTime.now());
        sensor.setVersion("1.0.1");
        sensor.setMeasurements(null);
    }

    public List<Sensor> findAll() {
        return sensorRepository.findAll();
    }

    public Sensor findByName(String name) {
        return sensorRepository.findByName(name).orElse(null);
    }
}
