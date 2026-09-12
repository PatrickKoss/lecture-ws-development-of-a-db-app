package course.museum.service;

import java.math.BigDecimal;

public record ExhibitCommand(String inventoryCode, String title, BigDecimal insuredValue) {}
