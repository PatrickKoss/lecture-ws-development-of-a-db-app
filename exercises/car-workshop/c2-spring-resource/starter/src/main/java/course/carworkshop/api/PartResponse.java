package course.carworkshop.api;

import course.carworkshop.domain.Part;
import java.math.BigDecimal;

public record PartResponse(
    Long id,
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
