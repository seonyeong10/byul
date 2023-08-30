package com.byul.domain.order;

import com.byul.config.enums.EnumField;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MilkType implements EnumField {

    REGULAR("0", "일반"),
    FREEFAT("1", "무지방"),
    HIGHFAT("2", "고지방"),
    LOWFAT("3", "저지방"),
    OAT("4", "귀리/오트"),
    ALMOND("5", "아몬드");

    private final String key;

    private final String value;
}
