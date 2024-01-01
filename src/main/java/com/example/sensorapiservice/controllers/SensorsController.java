package com.example.sensorapiservice.controllers;

import com.example.sensorapiservice.DTO.SensorDTO;
import com.example.sensorapiservice.models.Sensor;
import com.example.sensorapiservice.services.SensorService;
import com.example.sensorapiservice.utils.exceptions.SensorCreatedException;
import com.example.sensorapiservice.utils.responses.SensorErrorResponse;
import com.example.sensorapiservice.utils.SensorValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sensors")
public class SensorsController {
    private final SensorValidator sensorValidator;
    private final SensorService sensorService;
    private final ModelMapper modelMapper;


    @GetMapping()
    public List<SensorDTO> getSensors() {
        return sensorService.findAll().stream()
                .map(this::convertToSensorDTO)
                .toList();
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registrationSensor(@RequestBody @Valid SensorDTO sensorDTO,
                                                         BindingResult bindingResult) {
        sensorValidator.validate(convertToSensor(sensorDTO), bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder msgErrors = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError error : errors) {
                msgErrors.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage());
            }

            throw new SensorCreatedException(msgErrors.toString());
        }

        sensorService.save(sensorDTO);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    private SensorDTO convertToSensorDTO(Sensor sensor) {
        return modelMapper.map(sensor, SensorDTO.class);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> exceptionHandler(SensorCreatedException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
