package course.museum;

import java.math.BigDecimal;

public record Exhibit(Long id, String inventoryCode, String title, BigDecimal insuredValue) {}
