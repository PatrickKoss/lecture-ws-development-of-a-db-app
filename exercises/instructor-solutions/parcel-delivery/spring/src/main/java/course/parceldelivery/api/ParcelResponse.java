package course.parceldelivery.api;

import io.swagger.v3.oas.annotations.media.Schema;

import course.parceldelivery.domain.Parcel;
import java.math.BigDecimal;

@Schema(description = "API-Darstellung der Ressource Parcel")
public record ParcelResponse(
    @Schema(
            description = "Vom Server vergebene ID",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY)
        Long id,
    @Schema(description = "Eindeutiger Sendungscode", example = "PK-99") String trackingCode,
    @Schema(description = "Name des Empfängers", example = "Erika Muster") String recipient,
    @Schema(description = "Gewicht in Kilogramm", example = "2.50") BigDecimal weight) {
  public static ParcelResponse from(Parcel value) {
    return new ParcelResponse(value.id(), value.trackingCode(), value.recipient(), value.weight());
  }
}
