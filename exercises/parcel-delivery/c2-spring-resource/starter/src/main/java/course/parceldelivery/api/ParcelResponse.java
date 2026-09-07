package course.parceldelivery.api;

import io.swagger.v3.oas.annotations.media.Schema;

import course.parceldelivery.domain.Parcel;
import java.math.BigDecimal;

@Schema(description = "Öffentliche Darstellung der Ressource Parcel")
public record ParcelResponse(
    @Schema(description = "Vom Server vergebene ID", example = "1",
        accessMode = Schema.AccessMode.READ_ONLY) Long id, String trackingCode, String recipient, BigDecimal weight) {
  public static ParcelResponse from(Parcel value) {
    return new ParcelResponse(value.id(), value.trackingCode(), value.recipient(), value.weight());
  }
}
