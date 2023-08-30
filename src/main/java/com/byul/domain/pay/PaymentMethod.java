package com.byul.domain.pay;

import com.byul.config.enums.EnumField;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum PaymentMethod implements EnumField {

    KAKAO("KAKAO", "카카오페이");

    private final String key;
    private final String value;
}
