package course.carworkshop.service;

import java.math.BigDecimal;

public record PartCommand(
    String partNumber,
    String name,
    String category,
    String shelfCode,
    Integer stockQuantity,
    Integer reorderLevel,
    BigDecimal listPrice) {}
