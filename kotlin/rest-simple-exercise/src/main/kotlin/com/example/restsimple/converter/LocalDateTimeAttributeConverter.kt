package com.example.restsimple.converter

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
class LocalDateTimeAttributeConverter : AttributeConverter<LocalDateTime?, Long?> {
    override fun convertToDatabaseColumn(locDateTime: LocalDateTime?): Long? {
        return locDateTime?.atZone(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()
    }

    override fun convertToEntityAttribute(sqlTimestamp: Long?): LocalDateTime? {
        return if (sqlTimestamp == null) null else Instant.ofEpochMilli(sqlTimestamp).atZone(ZoneId.systemDefault())
            .toLocalDateTime()
    }
}
