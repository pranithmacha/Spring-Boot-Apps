package com.async.demo.service;

import com.async.demo.dto.ProductsServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class AsyncService {

    private RestService restService;

    private AtomicInteger count = new AtomicInteger();

    private Map<String, String> myaaappp = new HashMap<>();

    @Value("${products.service.url}")
    private String productsServiceURL;

    @Value("${weather.service.url}")
    private String weatherServiceURL;

    public void callRestService(int numOfCalls) {
        for (int i = 0; i < numOfCalls; i++) {
            restService.get(productsServiceURL).addCallback(
                    new ListenableFutureCallback<String>() {
                        @Override
                        public void onFailure(Throwable ex) {
                            log.error("Error while getting products from Products Service");
                        }

                        @Override
                        public void onSuccess(String result) {
                            log.info("response is ::: " + count.incrementAndGet() + result);
                        }
                    }
            );
        }
    }

    public ProductsServiceResponse callRestService() {
        restService.get(productsServiceURL).addCallback(
                new ListenableFutureCallback<String>() {
                    @Override
                    public void onFailure(Throwable ex) {
                        log.error("Error while getting products from Products Service");
                    }

                    @Override
                    public void onSuccess(String result) {
                        myaaappp.put("key", result);
                        log.info("response is ::: " + count.incrementAndGet() + result);
                    }
                }
        );
        return null;
    }

    @Autowired
    public AsyncService(RestService restService) {
        this.restService = restService;
    }
}
