package course.parceldelivery.domain;

import java.math.BigDecimal;

public record Parcel(Long id, String trackingCode, String recipient, BigDecimal weight) {}
