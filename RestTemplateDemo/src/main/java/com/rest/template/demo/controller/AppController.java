package com.rest.template.demo.controller;

import com.rest.template.demo.dto.ProductDTO;
import com.rest.template.demo.dto.ProductResponseDTO;
import com.rest.template.demo.exception.RestException;
import com.rest.template.demo.service.RestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class AppController {

    private RestService restService;

    @Value("${api.url}")
    private String apiURL;

    @GetMapping("/products")
    public List<ProductDTO> getProducts() {
        List<ProductDTO> products = new ArrayList<>();
        try {
            String response = restService.get(null, apiURL);
            ProductResponseDTO productResponse = restService.createResponse(response, ProductResponseDTO.class);
            products = productResponse.getProducts();
        } catch (RestException rEx) {
            log.error("Error while getting product information from service", rEx);
        }
        return products;
    }

    @PostMapping("/products")
    public List<ProductDTO> addProduct(@RequestBody ProductDTO product) {
        List<ProductDTO> products = new ArrayList<>();
        try {
            HttpEntity<ProductDTO> entity = new HttpEntity<>(product);
            String response = restService.post(entity, apiURL);
            ProductResponseDTO productResponse = restService.createResponse(response, ProductResponseDTO.class);
            products = productResponse.getProducts();
        } catch (RestException rEx) {
            log.error("Error while getting product information from service", rEx);
        }
        return products;
    }

    @Autowired
    public AppController(RestService restService) {
        this.restService = restService;
    }
}
