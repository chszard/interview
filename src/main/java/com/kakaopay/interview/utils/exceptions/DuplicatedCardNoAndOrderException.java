package com.kakaopay.interview.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicatedCardNoAndOrderException extends RuntimeException {
    public DuplicatedCardNoAndOrderException(String message) {
        super(message);
    }
}
