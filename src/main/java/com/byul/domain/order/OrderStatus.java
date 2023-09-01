package com.byul.domain.order;

import com.byul.config.enums.EnumField;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus implements EnumField {
    ORDER("ORDER", "주문완료"),
    PAYED("PAYED", "결제완료"),
    READY("READY", "준비중"),
    FIN("FIN", "준비완료"),
    PICKED("PICKED", "픽업완료"),
    CANCEL("CANCEL", "취소완료");

    private final String key;
    private final String value;
}
