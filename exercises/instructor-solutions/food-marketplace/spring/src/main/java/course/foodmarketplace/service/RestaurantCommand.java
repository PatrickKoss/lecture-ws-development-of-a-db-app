package course.foodmarketplace.service;

import java.math.BigDecimal;

public record RestaurantCommand(
    String partnerNumber,
    String name,
    String street,
    String postalCode,
    String city,
    BigDecimal commissionRate,
    Boolean active) {}
