package com.kakaopay.interview.utils.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
public class ExceptionResponse {
    private Date timestamp;
    private String message;
    private String detail;

    public ExceptionResponse(Date timestamp, String message, String detail) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.detail = detail;
    }
}
