package com.byul.domain.converter;

import com.byul.config.enums.AbstractEnumAttributeConverter;
import com.byul.domain.Membership;
import jakarta.persistence.Converter;

@Converter
public class MembershipConverter extends AbstractEnumAttributeConverter<Membership> {
    public MembershipConverter() {
        super(Membership.class);
    }
}
