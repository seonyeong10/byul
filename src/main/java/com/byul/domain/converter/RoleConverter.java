package com.byul.domain.converter;

import com.byul.config.enums.AbstractEnumAttributeConverter;
import com.byul.domain.Role;
import jakarta.persistence.Converter;

@Converter
public class RoleConverter extends AbstractEnumAttributeConverter<Role> {
    public RoleConverter() {
        super(Role.class);
    }
}
