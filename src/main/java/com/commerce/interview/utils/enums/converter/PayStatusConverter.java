package com.commerce.interview.utils.enums.converter;

import com.commerce.interview.business.pay.entity.PayStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class PayStatusConverter implements AttributeConverter<PayStatus, String> {
    @Override
    public String convertToDatabaseColumn(PayStatus payStatus) {
        if (payStatus == null) {
            return null;
        }
        return payStatus.getStatus();
    }

    @Override
    public PayStatus convertToEntityAttribute(String payStatus) {
        if (payStatus == null) {
            return null;
        }

        return Stream.of(PayStatus.values())
                .filter(c -> c.getStatus().equals(payStatus))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
