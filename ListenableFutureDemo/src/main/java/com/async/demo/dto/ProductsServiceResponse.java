package com.async.demo.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductsServiceResponse {
    List<Product> products = new ArrayList<>();
}
