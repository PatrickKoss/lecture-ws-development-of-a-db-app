package course.carworkshop.api;

import io.swagger.v3.oas.annotations.media.Schema;

import course.carworkshop.domain.Part;
import java.math.BigDecimal;

@Schema(description = "Öffentliche Darstellung der Ressource Part")
public record PartResponse(
    @Schema(description = "Vom Server vergebene ID", example = "1",
        accessMode = Schema.AccessMode.READ_ONLY) Long id,
    String partNumber,
    String name,
    String category,
    String shelfCode,
    Integer stockQuantity,
    Integer reorderLevel,
    BigDecimal listPrice) {
  public static PartResponse from(Part value) {
    return new PartResponse(
        value.id(),
        value.partNumber(),
        value.name(),
        value.category(),
        value.shelfCode(),
        value.stockQuantity(),
        value.reorderLevel(),
        value.listPrice());
  }
}
