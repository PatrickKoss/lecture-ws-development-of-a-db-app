package course.foodmarketplace.domain;

import java.math.BigDecimal;

public record Restaurant(
    Long id,
    String partnerNumber,
    String name,
    String street,
    String postalCode,
    String city,
    BigDecimal commissionRate,
    Boolean active) {}
