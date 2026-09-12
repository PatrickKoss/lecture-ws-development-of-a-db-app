package course.parceldelivery.service;

import java.math.BigDecimal;

public record ParcelCommand(String trackingCode, String recipient, BigDecimal weight) {}
