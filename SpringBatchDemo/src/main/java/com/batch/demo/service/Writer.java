package com.batch.demo.service;

import java.util.List;

import com.batch.demo.data.DummyCar;
import com.batch.demo.repository.DummyCarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@StepScope
@Component
public class Writer implements ItemWriter<DummyCar> {

    private DummyCarRepository dummyCarRepository;

    @Override
    public void write(List<? extends DummyCar> dummyCars) {
//        log.info("writing");
        dummyCarRepository.saveAll(dummyCars);
    }

    @Autowired
    public Writer(DummyCarRepository dummyCarRepository) {
        this.dummyCarRepository = dummyCarRepository;
    }
}