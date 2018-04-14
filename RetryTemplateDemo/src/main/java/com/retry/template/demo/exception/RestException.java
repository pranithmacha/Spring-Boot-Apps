package com.retry.template.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class RestException extends RuntimeException {
    String errorMessage;
}
