package com.example.restsimple.response;

import javax.validation.constraints.NotNull;

public class ErrorResponse {
    @NotNull(message = "message is required")
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
