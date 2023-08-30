package com.byul.domain;

import com.byul.config.enums.EnumField;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role implements EnumField {
    ADMIN("0", "Admin"),
    USER("1", "User");

    private final String key;
    private final String value;
}
