package com.batch.demo.service;

import com.batch.demo.data.Car;
import com.batch.demo.data.DummyCar;
import com.batch.demo.exception.RestException;
import com.batch.demo.repository.DummyCarRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@StepScope
@NoArgsConstructor
public class Processor implements ItemProcessor<Car, DummyCar> {

    private RestService restService;

    @Override
    public DummyCar process(Car car) {
//        log.info("processing");
        try {
            String serviceURL = "http://127.0.0.1:5015/products";
            restService.get(serviceURL);
        } catch (RestException rEx) {
            log.error("error while calling service");
        }
        DummyCar dummyCar = new DummyCar();
        dummyCar.setColor(car.getColor());
        dummyCar.setMake(car.getMake());
        dummyCar.setName(car.getName());
        dummyCar.setYear(car.getYear());
        dummyCar.setAutoEnabled(car.isAutoEnabled());
        return dummyCar;
    }

    @Autowired
    public Processor(RestService restService) {
        this.restService = restService;
    }
}