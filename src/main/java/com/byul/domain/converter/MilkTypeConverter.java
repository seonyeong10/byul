package com.byul.domain.converter;

import com.byul.config.enums.AbstractEnumAttributeConverter;
import com.byul.domain.order.MilkType;
import jakarta.persistence.Converter;

@Converter
public class MilkTypeConverter extends AbstractEnumAttributeConverter<MilkType> {
    public MilkTypeConverter() { super(MilkType.class); }
}
