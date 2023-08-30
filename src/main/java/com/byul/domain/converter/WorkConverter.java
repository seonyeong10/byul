package com.byul.domain.converter;

import com.byul.config.enums.AbstractEnumAttributeConverter;
import com.byul.domain.staff.Work;
import jakarta.persistence.Converter;

@Converter
public class WorkConverter extends AbstractEnumAttributeConverter<Work> {
    public WorkConverter() {
        super(Work.class);
    }
}
