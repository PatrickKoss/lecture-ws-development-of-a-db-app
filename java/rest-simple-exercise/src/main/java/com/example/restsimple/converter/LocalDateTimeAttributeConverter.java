package com.example.restsimple.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Converter(autoApply = true)
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Long> {

    @Override
    public Long convertToDatabaseColumn(LocalDateTime locDateTime) {
        return locDateTime == null ? null : locDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Long sqlTimestamp) {
        return sqlTimestamp == null ? null : Instant.ofEpochMilli(sqlTimestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
