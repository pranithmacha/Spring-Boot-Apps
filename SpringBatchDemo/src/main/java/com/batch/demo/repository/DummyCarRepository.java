package com.batch.demo.repository;

import com.batch.demo.data.DummyCar;
import org.springframework.data.repository.CrudRepository;

public interface DummyCarRepository extends CrudRepository<DummyCar, Long> {

    DummyCar findCarById(long id);
}

