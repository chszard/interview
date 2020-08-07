package com.commerce.interview.utils.exceptions;


import com.commerce.interview.common.handler.MessageHandler;
import com.commerce.interview.utils.enums.code.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    @Autowired
    private MessageHandler messageHandler;

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(MessageType message, String args) {
        new UserNotFoundException(String.format(messageHandler.getMessage(message), args));
    }
}
