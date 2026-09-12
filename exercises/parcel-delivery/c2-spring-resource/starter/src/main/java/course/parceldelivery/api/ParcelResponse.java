package course.parceldelivery.api;

import course.parceldelivery.domain.Parcel;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "Öffentliche Darstellung von Pakete")
public record ParcelResponse(
    @Schema(
            description = "Vom Server vergebene ID",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY,
            requiredMode = Schema.RequiredMode.REQUIRED)
        Long id,
    @Schema(
            description = "Eindeutiger Sendungscode",
            example = "PK-99",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String trackingCode,
    @Schema(
            description = "Name des Empfängers",
            example = "Test Empfänger",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String recipient,
    @Schema(
            description = "Gewicht in Kilogramm",
            example = "3.5",
            requiredMode = Schema.RequiredMode.REQUIRED)
        BigDecimal weight) {
  public static ParcelResponse from(Parcel value) {
    return new ParcelResponse(value.id(), value.trackingCode(), value.recipient(), value.weight());
  }
}
