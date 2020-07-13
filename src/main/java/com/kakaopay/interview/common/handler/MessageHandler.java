package com.kakaopay.interview.common.handler;

import com.kakaopay.interview.utils.enums.code.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageHandler {
    private final MessageSource messageSource;

    public String getMessage(MessageType messageType) {
        return messageSource.getMessage(messageType.getMessage(), null, LocaleContextHolder.getLocale());
    }

    public String getMessage(MessageType messageType, Locale locale) {
        return messageSource.getMessage(messageType.getMessage(), null, locale);
    }

    public String getMessage(MessageType messageType, Locale locale, Object... args) {
        return messageSource.getMessage(messageType.getMessage(), args, locale);
    }
}
