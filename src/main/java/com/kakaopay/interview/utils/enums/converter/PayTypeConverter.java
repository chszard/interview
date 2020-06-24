package com.kakaopay.interview.utils.enums.converter;

import com.kakaopay.interview.business.pay.entity.PayType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class PayTypeConverter implements AttributeConverter<PayType, String> {
    @Override
    public String convertToDatabaseColumn(PayType orderType) {
        if (orderType == null) {
            return null;
        }
        return orderType.getType();
    }

    @Override
    public PayType convertToEntityAttribute(String payType) {
        if (payType == null) {
            return null;
        }

        return Stream.of(PayType.values())
                .filter(c -> c.getType().equals(payType))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
