package com.example.restsimple.response;

import jakarta.validation.constraints.NotNull;

public class ErrorResponse {
    @NotNull(message = "message is required")
    private String message;
    private String details;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public ErrorResponse(String message, String details) {
        this.message = message;
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
