package com.batch.demo.service;

import com.batch.demo.exception.RestException;
import org.springframework.http.HttpEntity;

public interface RestService {

    String get(String URL) throws RestException;

    String post(HttpEntity httpEntity, String URL) throws RestException;

    <T> T createResponse(String content, Class<T> valueType) throws RestException;
}
