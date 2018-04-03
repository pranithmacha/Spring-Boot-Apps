package com.rest.template.demo.service;

import com.rest.template.demo.exception.RestException;
import org.springframework.http.HttpEntity;

public interface RestService {

    String get(HttpEntity httpEntity, String URL) throws RestException;

    String post(HttpEntity httpEntity, String URL) throws RestException;

    <T> T createResponse(String content, Class<T> valueType) throws RestException;
}
