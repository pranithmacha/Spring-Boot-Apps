package com.async.demo.service;

import com.async.demo.dto.ProductsServiceResponse;
import com.async.demo.exception.RestException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Slf4j
@Service
public class RestServiceImpl implements RestService {

    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    @Async
    @Override
    public ListenableFuture<ProductsServiceResponse> get(String URL) {
        String responseBody = call(null, URL, HttpMethod.GET);
        ProductsServiceResponse productsServiceResponse = createResponse(responseBody, ProductsServiceResponse.class);
        return new AsyncResult<>(productsServiceResponse);
    }

    @Override
    public String post(HttpEntity httpEntity, String URL) {
        return call(httpEntity, URL, HttpMethod.POST);
    }

    public <T> T createResponse(String content, Class<T> valueType) throws RestException {
        T response;
        try {
            response = objectMapper.readValue(content, valueType);
        } catch (IOException ioEx) {
            throw new RestException("Error while parsing JSON");
        }
        return response;
    }

    private String call(HttpEntity httpEntity, String URL, HttpMethod httpMethod) {
        String responseBody;
        ResponseEntity<String> responseEntity;
        try {
            responseEntity = restTemplate.exchange(URL, httpMethod, httpEntity,
                    new ParameterizedTypeReference<String>() {
                    });
            responseBody = responseEntity.getBody();
        } catch (ResourceAccessException reAEx) {
            throw new RestException("Network error while calling service");
        } catch (HttpServerErrorException ex) {
            throw new RestException("Not a valid response from service");
        } catch (Exception ex) {
            throw new RestException("Fatal error occurred getting response from service");
        }
        return responseBody;
    }

    @Autowired
    public RestServiceImpl(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }
}
