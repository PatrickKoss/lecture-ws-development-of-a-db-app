package course.museum.api;

import course.museum.domain.Exhibit;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "Öffentliche Darstellung von Exponate")
public record ExhibitResponse(
    @Schema(
            description = "Vom Server vergebene ID",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY,
            requiredMode = Schema.RequiredMode.REQUIRED)
        Long id,
    @Schema(
            description = "Eindeutiger Inventarcode",
            example = "EX-99",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String inventoryCode,
    @Schema(
            description = "Titel des Exponats",
            example = "Testexponat",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String title,
    @Schema(
            description = "Versicherungswert",
            example = "1000.00",
            requiredMode = Schema.RequiredMode.REQUIRED)
        BigDecimal insuredValue) {
  public static ExhibitResponse from(Exhibit value) {
    return new ExhibitResponse(
        value.id(), value.inventoryCode(), value.title(), value.insuredValue());
  }
}
