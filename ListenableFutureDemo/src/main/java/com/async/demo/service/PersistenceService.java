package com.async.demo.service;

import com.async.demo.dto.FinalObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PersistenceService {

    public void persistFinalObject(FinalObject finalObject) {
        if (!finalObject.getUUID().equals(finalObject.getProductsServiceResponse().getTraceId()) ||
                !finalObject.getUUID().equals(finalObject.getWeatherServiceResponse().getTraceId())) {
            log.info(finalObject.getUUID() + " - " + finalObject.getProductsServiceResponse().getTraceId() + " - " +
                    finalObject.getWeatherServiceResponse().getTraceId()
            );
        }
    }
}
