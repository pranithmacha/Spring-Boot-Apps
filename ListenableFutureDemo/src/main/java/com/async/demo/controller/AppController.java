package com.async.demo.controller;

import com.async.demo.dto.ProductsServiceResponse;
import com.async.demo.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    private AsyncService asyncService;

    @GetMapping
    public String startAsyncDemo() {
        ProductsServiceResponse productsServiceResponse = asyncService.callRestService();
        return productsServiceResponse.getProducts().get(0).getName();
    }

    @Autowired
    public AppController(AsyncService asyncService) {
        this.asyncService = asyncService;
    }
}
