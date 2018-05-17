package com.batch.demo.service;

import com.batch.demo.data.Car;
import com.batch.demo.repository.CarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class Reader implements ItemReader<Car> {

    public static int count;
    private int numOfRecords;
    private int start;

    @Autowired
    private CarRepository carRepository;

    @Override
    public Car read() {
//        log.info("reading");
        count++;
        if (count > this.numOfRecords)
            return null;
        Car car = carRepository.findCarById(count + start);
        return car;
    }

    public Reader(int numOfRecords, int start) {
        this.numOfRecords = numOfRecords;
        this.start = start;
    }
}