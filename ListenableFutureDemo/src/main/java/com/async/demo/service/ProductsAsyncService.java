package com.async.demo.service;

import com.async.demo.dto.FinalObject;
import com.async.demo.dto.ProductsServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;


@Slf4j
@Service
public class ProductsAsyncService {

    private RestService restService;
    private WeatherAsyncService weatherAsyncService;

    @Value("${products.service.url}")
    private String productsServiceURL;

    public ProductsServiceResponse callProductsService(FinalObject finalObject) {
        String prodServiceURL = productsServiceURL + "/" + finalObject.getUUID();
        restService.get(prodServiceURL).addCallback(
                new ListenableFutureCallback<String>() {
                    @Override
                    public void onFailure(Throwable ex) {
                        log.error("Error while getting products from Products Service");
                    }

                    @Override
                    public void onSuccess(String result) {
                        ProductsServiceResponse productsServiceResponse = restService.createResponse(result,
                                ProductsServiceResponse.class);
                        if (!productsServiceResponse.getTraceId().equals(finalObject.getUUID()))
                            System.out.println("products service " + finalObject.getUUID() + " "
                                    + productsServiceResponse.getTraceId());
                        finalObject.setProductsServiceResponse(productsServiceResponse);
                        weatherAsyncService.callWeatherService(finalObject);
                    }
                }
        );
        return null;
    }

    @Autowired
    public ProductsAsyncService(RestService restService,
                                WeatherAsyncService weatherAsyncService) {
        this.restService = restService;
        this.weatherAsyncService = weatherAsyncService;
    }
}

