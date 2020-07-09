package com.kakaopay.interview.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DtoArgumentNotValidException extends RuntimeException {
    public DtoArgumentNotValidException(String message) {
        super(message);
    }
}
