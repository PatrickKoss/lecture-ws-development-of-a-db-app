package course.template.domain;

import java.math.BigDecimal;

public record Resource(Long id, String resourceCode, String name, BigDecimal measure) {}
