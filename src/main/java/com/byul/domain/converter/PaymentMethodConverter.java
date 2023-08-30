package com.byul.domain.converter;

import com.byul.config.enums.AbstractEnumAttributeConverter;
import com.byul.domain.pay.PaymentMethod;

public class PaymentMethodConverter extends AbstractEnumAttributeConverter<PaymentMethod> {
    public PaymentMethodConverter() {
        super(PaymentMethod.class);
    }
}
