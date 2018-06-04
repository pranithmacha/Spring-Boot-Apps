package com.async.demo.dto;

import lombok.Data;

@Data
public class WeatherServiceResponse {
    private String traceId;
    private String temperature;
    private String humidity;
    private String city;
    private String country;
    private String description;
}
