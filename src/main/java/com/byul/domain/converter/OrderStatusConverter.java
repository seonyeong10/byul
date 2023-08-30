package com.byul.domain.converter;

import com.byul.config.enums.AbstractEnumAttributeConverter;
import com.byul.domain.order.OrderStatus;
import jakarta.persistence.Converter;

@Converter
public class OrderStatusConverter extends AbstractEnumAttributeConverter<OrderStatus> {
    public OrderStatusConverter() {
        super(OrderStatus.class);
    }
}
