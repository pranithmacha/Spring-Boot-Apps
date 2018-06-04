package com.async.demo.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductsServiceResponse {
    String traceId;
    List<Product> products = new ArrayList<>();
}
