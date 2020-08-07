package com.commerce.interview.utils.enums.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@Getter
public enum MessageType {

    // [0000~0999] common message
    SUCCESS_REQUEST("0000", HttpStatus.OK, "response.request"),
    NO_CONTENT("0100", HttpStatus.NO_CONTENT, "response.noContent"),
    NOT_FOUND_USER("0101", HttpStatus.NOT_FOUND, "response.notFoundUser")
    ;
    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    MessageType(String code, HttpStatus httpStatus) {
        this(code, httpStatus, null);
    }

    MessageType(String code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public static MessageType of(String code) {
        return Arrays.stream(MessageType.values())
                .filter(v -> v.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
