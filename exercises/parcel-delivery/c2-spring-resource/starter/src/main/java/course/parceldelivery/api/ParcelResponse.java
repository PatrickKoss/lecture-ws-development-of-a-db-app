package course.parceldelivery.api;

import course.parceldelivery.domain.Parcel;
import java.math.BigDecimal;

public record ParcelResponse(Long id, String trackingCode, String recipient, BigDecimal weight) {
  public static ParcelResponse from(Parcel value) {
    return new ParcelResponse(value.id(), value.trackingCode(), value.recipient(), value.weight());
  }
}
