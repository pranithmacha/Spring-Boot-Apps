package com.async.demo.controller;

import com.async.demo.dto.FinalObject;
import com.async.demo.dto.ProductsServiceResponse;
import com.async.demo.service.AsyncService;
import com.async.demo.service.ProductsAsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    private AsyncService asyncService;
    private ProductsAsyncService productsAsyncService;

    @GetMapping
    public String startAsyncDemo() {
//        ProductsServiceResponse productsServiceResponse = asyncService.callRestService();
        for (int i = 0; i < 100; i++) {
            String UUID = java.util.UUID.randomUUID().toString();
            FinalObject finalObject = new FinalObject();
            finalObject.setUUID(UUID);
            ProductsServiceResponse resp = productsAsyncService.callProductsService(finalObject);
        }
        return "success";
    }

    @Autowired
    public AppController(AsyncService asyncService,
                         ProductsAsyncService productsAsyncService) {
        this.asyncService = asyncService;
        this.productsAsyncService = productsAsyncService;
    }
}
