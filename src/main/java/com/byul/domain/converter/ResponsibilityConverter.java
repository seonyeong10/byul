package com.byul.domain.converter;

import com.byul.config.enums.AbstractEnumAttributeConverter;
import com.byul.domain.staff.Responsibility;
import jakarta.persistence.Converter;

@Converter
public class ResponsibilityConverter extends AbstractEnumAttributeConverter<Responsibility> {
    public ResponsibilityConverter() {
        super(Responsibility.class);
    }
}
