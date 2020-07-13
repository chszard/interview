package com.kakaopay.interview.utils.exceptions;


import com.kakaopay.interview.common.handler.MessageHandler;
import com.kakaopay.interview.utils.enums.code.MessageType;
import lombok.NoArgsConstructor;
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
