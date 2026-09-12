package course.carworkshop;

import java.math.BigDecimal;

public record Part(
    Long id,
    String partNumber,
    String name,
    String category,
    String shelfCode,
    Integer stockQuantity,
    Integer reorderLevel,
    BigDecimal listPrice) {}
