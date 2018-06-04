package com.async.demo.service;

import com.async.demo.dto.FinalObject;
import com.async.demo.dto.WeatherServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Service
public class WeatherAsyncService {

    private RestService restService;
    private PersistenceService persistenceService;

    @Value("${weather.service.url}")
    private String weatherServiceURL;

    public void callWeatherService(FinalObject finalObject) {
        String newURL = weatherServiceURL + "/" + finalObject.getUUID();
        restService.get(newURL).addCallback(
                new ListenableFutureCallback<String>() {
                    @Override
                    public void onFailure(Throwable ex) {
                        log.error("Error while getting products from Weather service");
                    }

                    @Override
                    public void onSuccess(String result) {
                        WeatherServiceResponse weatherServiceResponse = restService.createResponse(result,
                                WeatherServiceResponse.class);
                        finalObject.setWeatherServiceResponse(weatherServiceResponse);
                        if (!weatherServiceResponse.getTraceId().equals(finalObject.getUUID()))
                            System.out.println("weather service " + finalObject.getUUID() + " "
                                    + weatherServiceResponse.getTraceId());
                        persistenceService.persistFinalObject(finalObject);
                    }
                }
        );
    }

    @Autowired
    public WeatherAsyncService(RestService restService,
                               PersistenceService persistenceService) {
        this.restService = restService;
        this.persistenceService = persistenceService;
    }
}
