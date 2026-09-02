package com.example.restsimple.response;

import java.util.Map;

public class ErrorResponse {
    private String code;
    private String message;
    private String correlationId;
    private Map<String, String> fields;

    public ErrorResponse(String code, String message, String correlationId) {
        this(code, message, correlationId, Map.of());
    }

    public ErrorResponse(String code, String message, String correlationId, Map<String, String> fields) {
        this.code = code;
        this.message = message;
        this.correlationId = correlationId;
        this.fields = fields;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public Map<String, String> getFields() {
        return fields;
    }

    public void setFields(Map<String, String> fields) {
        this.fields = fields;
    }
}
