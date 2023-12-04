package com.example.sensorapiservice.utils;

import com.example.sensorapiservice.models.Sensor;
import com.example.sensorapiservice.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SensorValidator implements Validator {
    private final SensorRepository sensorRepository;

    @Autowired
    public SensorValidator(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Sensor sensor = (Sensor) o;

        if (sensorRepository.findByName(sensor.getName()).isPresent()) {
            errors.rejectValue("name", "", "Сенсор с таким именем уже существует");
        }
    }
}
