package com.example.restsimple.response;

public class ErrorResponse {
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