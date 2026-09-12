package course.template.service;

import java.math.BigDecimal;

public record ResourceCommand(String resourceCode, String name, BigDecimal measure) {}
