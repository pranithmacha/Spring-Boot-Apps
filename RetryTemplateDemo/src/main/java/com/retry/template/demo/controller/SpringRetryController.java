package com.retry.template.demo.controller;

import com.retry.template.demo.dto.WSResponse;
import com.retry.template.demo.exception.RestException;
import com.retry.template.demo.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringRetryController {

    private RestService restService;

    @Value("${products.service.200.url}")
    private String PRODUCT_SERVICE_200_URL;

    @Value("${products.service.500.url}")
    private String PRODUCT_SERVICE_500_URL;

    @GetMapping("/200")
    public ResponseEntity<WSResponse> invokeExternalService200() {
        restService.get(PRODUCT_SERVICE_200_URL);
        return new ResponseEntity<>(new WSResponse(), HttpStatus.OK);
    }

    @GetMapping("/500")
    public ResponseEntity<WSResponse> invokeExternalService500() {
        WSResponse response = new WSResponse();
        try {
            restService.get(PRODUCT_SERVICE_500_URL);
        } catch (RestException rEx) {
            response.setMessage(rEx.getErrorMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Autowired
    public SpringRetryController(RestService restService) {
        this.restService = restService;
    }
}
