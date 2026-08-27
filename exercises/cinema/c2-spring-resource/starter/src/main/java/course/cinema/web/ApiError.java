package course.cinema.web;

import java.util.Map;

public record ApiError(
    String code, String message, String correlationId, Map<String, String> fields) {}
