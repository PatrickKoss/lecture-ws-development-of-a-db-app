package com.example.restsimple.response;

import java.util.Map;

public record ErrorResponse(
        String code,
        String message,
        String correlationId,
        Map<String, String> fields) {}
