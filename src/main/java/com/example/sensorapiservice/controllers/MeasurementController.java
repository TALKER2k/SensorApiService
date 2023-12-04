package com.example.sensorapiservice.controllers;

import com.example.sensorapiservice.DTO.MeasurementDTO;
import com.example.sensorapiservice.models.Measurement;
import com.example.sensorapiservice.services.MeasurementService;
import com.example.sensorapiservice.services.SensorService;
import com.example.sensorapiservice.utils.exceptions.MeasurementCreatedException;
import com.example.sensorapiservice.utils.exceptions.MeasurementNotFoundException;
import com.example.sensorapiservice.utils.responses.MeasurementErrorResponse;
import com.example.sensorapiservice.utils.responses.SensorErrorResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {
    private final ModelMapper modelMapper;
    private final MeasurementService measurementService;
    private  final SensorService sensorService;

    @Autowired
    public MeasurementController(ModelMapper modelMapper, MeasurementService measurementService, SensorService sensorService) {
        this.modelMapper = modelMapper;
        this.measurementService = measurementService;
        this.sensorService = sensorService;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addNewMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder msgErrors = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError error : errors) {
                msgErrors.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage());
            }

            throw new MeasurementCreatedException(msgErrors.toString());
        }

        if (sensorService.findByName(measurementDTO.getSensor().getName()) == null) {
            throw new MeasurementNotFoundException();
        }

        measurementService.save(convertToMeasurement(measurementDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        Measurement measurement = modelMapper.map(measurementDTO, Measurement.class);
        measurement.setSensor(sensorService.findByName(measurementDTO.getSensor().getName()));
        return measurement;
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> exceptionHandler(MeasurementNotFoundException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                "Sensor not found",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> exceptionHandler(MeasurementCreatedException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @GetMapping()
    public List<MeasurementDTO> getListMeasurements(@RequestParam(value = "sensor", required = false) String sensorName) {
        if (sensorName == null) {
            return measurementService.findAll().stream()
                    .map(this::convertToMeasurementDTO)
                    .toList();
        } else {
            return measurementService.findAll().stream()
                    .filter(m -> m.getSensor().getName().equals(sensorName))
                    .map(this::convertToMeasurementDTO)
                    .toList();
        }
    }

    @GetMapping("/rainyDaysCount")
    public Long rainyDaysCount() {
        return measurementService.findAll().stream()
                .filter(Measurement::isRaining)
                .count();
    }
}
