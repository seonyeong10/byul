package com.byul.domain;

import com.byul.config.enums.EnumField;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Membership implements EnumField {

    WELCOME("0", "Welcome"),
    GREEN("1", "Green"),
    GOLD("2", "Gold");

    private final String key;
    private final String value;
}
