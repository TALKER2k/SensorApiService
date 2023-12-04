package com.example.sensorapiservice;

import com.example.sensorapiservice.DTO.SensorDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class RestTemplateApp {
    public static void main(String[] args) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
//        String url = "http://localhost:8080/sensors/registration";
//        String url = "http://localhost:8080/measurements/add";
        String url = "http://localhost:8080/measurements?sensor=Sensor Test";
//        json.put("name", "Sensor Test");

//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);

//        for (int i = 0; i < 1000; i++) {
//            Map<String, Object> json = new LinkedHashMap<>();
//            Random random = new Random();
//            json.put("value", random.nextInt(-100, 100));
//            json.put("raining", random.nextBoolean());
//            SensorDTO sensorDTO = new SensorDTO();
//            sensorDTO.setName("Sensor Test");
//            json.put("sensor", sensorDTO);
//
//            HttpEntity<Map<String, Object>> request = new HttpEntity<>(json, headers);
//
//           restTemplate.postForObject(url, request, String.class);
//        }

        String response = restTemplate.getForObject(url, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response);
        StringBuilder sensorValue = new StringBuilder();
        for (int i = 0; i < jsonNode.size(); i++) {
            sensorValue.append(jsonNode.get(i).get("value")).append("\n");
        }

        System.out.println(response);
        System.out.println(sensorValue);
    }
}
