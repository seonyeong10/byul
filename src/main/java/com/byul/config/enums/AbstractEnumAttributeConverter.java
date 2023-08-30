package com.byul.config.enums;

import jakarta.persistence.AttributeConverter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class AbstractEnumAttributeConverter<T extends Enum<T> & EnumField> implements AttributeConverter<T, String> {

    private final Class<T> targetEnumClass;

    @Override
    public String convertToDatabaseColumn(T attribute) {
        return EnumUtils.toDBCode(attribute);
    }

    @Override
    public T convertToEntityAttribute(String dbData) {
        return EnumUtils.toEntityCode(targetEnumClass, dbData);
    }
}
