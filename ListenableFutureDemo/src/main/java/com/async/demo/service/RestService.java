package com.async.demo.service;

import com.async.demo.exception.RestException;
import org.springframework.http.HttpEntity;
import org.springframework.util.concurrent.ListenableFuture;

public interface RestService {

    ListenableFuture<String> get(String URL) throws RestException;

    String post(HttpEntity httpEntity, String URL) throws RestException;

    <T> T createResponse(String content, Class<T> valueType) throws RestException;
}
