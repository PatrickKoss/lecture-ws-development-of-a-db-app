package course.museum.api;

import course.museum.domain.Exhibit;
import java.math.BigDecimal;

public record ExhibitResponse(Long id, String inventoryCode, String title, BigDecimal insuredValue) {
  public static ExhibitResponse from(Exhibit value) {
    return new ExhibitResponse(value.id(), value.inventoryCode(), value.title(), value.insuredValue());
  }
}
