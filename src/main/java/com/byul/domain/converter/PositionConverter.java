package com.byul.domain.converter;

import com.byul.config.enums.AbstractEnumAttributeConverter;
import com.byul.domain.staff.Position;
import jakarta.persistence.Converter;

@Converter
public class PositionConverter extends AbstractEnumAttributeConverter<Position> {
    public PositionConverter() {
        super(Position.class);
    }
}
