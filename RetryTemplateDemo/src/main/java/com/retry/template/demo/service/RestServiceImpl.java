package com.retry.template.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retry.template.demo.exception.RestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Slf4j
@Service
public class RestServiceImpl implements RestService {

    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;
    private RetryTemplate retryTemplate;

    @Override
    public String get(String URL) {
        return call(null, URL, HttpMethod.GET);
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
            responseEntity = retryTemplate.execute(arg0 ->
                    retryApiCall(URL, httpMethod, httpEntity)
            );
            responseBody = responseEntity.getBody();
        } catch (ResourceAccessException reAEx) {
            throw new RestException("Network error while calling service");
        } catch (HttpServerErrorException ex) {
            throw new RestException("Not a valid response from service");
        } catch (Exception ex) {
            throw new RestException("Unhandled error occurred while getting response from service");
        }
        return responseBody;
    }

    private ResponseEntity<String> retryApiCall(String URL, HttpMethod httpMethod, HttpEntity httpEntity) {
        ResponseEntity<String> response = restTemplate.exchange(URL, httpMethod, httpEntity,
                new ParameterizedTypeReference<String>() {
                });
        if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR)
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        return response;
    }

    @Autowired
    public RestServiceImpl(RestTemplate restTemplate,
                           ObjectMapper objectMapper,
                           @Qualifier("custom") RetryTemplate retryTemplate) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.retryTemplate = retryTemplate;
    }
}
