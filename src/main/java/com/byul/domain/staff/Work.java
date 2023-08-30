package com.byul.domain.staff;

import com.byul.config.enums.EnumField;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Work implements EnumField {
    WORK("1", "재직"),
    RESIGN("2", "퇴직"),
    ABSENCE("3", "휴직");

    private final String key;
    private final String value;
}
