package com.commerce.interview.utils.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PayNotFoundException extends RuntimeException {
    public PayNotFoundException(String message) {
        super(message);
    }
}
