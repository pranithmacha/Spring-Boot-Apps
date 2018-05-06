package com.batch.demo.repository;

import com.batch.demo.data.Car;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car, Long> {

    Car findCarById(long id);
}
