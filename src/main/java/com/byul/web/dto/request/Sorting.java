package com.byul.web.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Sorting {
    DEFAULT("id"),
    NEWEST("period.startDate"),
    POPULARITY("orderCount");

    private final String value;
}
