package com.kakaopay.interview.utils.enums.converter;

import com.kakaopay.interview.business.order.entity.OrderStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class OrderStatusConverter implements AttributeConverter<OrderStatus, String> {
    @Override
    public String convertToDatabaseColumn(OrderStatus orderStatus) {
        if (orderStatus == null) {
            return null;
        }
        return orderStatus.getStatus();
    }

    @Override
    public OrderStatus convertToEntityAttribute(String orderStatus) {
        if (orderStatus == null) {
            return null;
        }

        return Stream.of(OrderStatus.values())
                .filter(c -> c.getStatus().equals(orderStatus))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
