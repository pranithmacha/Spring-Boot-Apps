package com.async.demo.dto;

import lombok.Data;

@Data
public class FinalObject {
    private String UUID;
    private ProductsServiceResponse productsServiceResponse;
    private WeatherServiceResponse weatherServiceResponse;
}
