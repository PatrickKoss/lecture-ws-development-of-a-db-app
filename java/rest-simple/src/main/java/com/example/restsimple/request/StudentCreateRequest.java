package com.example.restsimple.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class StudentCreateRequest {
    public StudentCreateRequest() {
    }

    public StudentCreateRequest(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    @NotNull(message = "name is required")
    @Size(max = 200, message = "name must not exceed 200 characters")
    protected String name;

    @NotNull(message = "lastName is required")
    @Size(max = 200, message = "lastName must not exceed 200 characters")
    protected String lastName;

    public String getName() {
        return this.name;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
