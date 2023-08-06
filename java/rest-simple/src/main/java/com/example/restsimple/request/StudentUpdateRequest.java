package com.example.restsimple.request;

public class StudentUpdateRequest extends StudentCreateRequest {
    public StudentUpdateRequest() {
    }

    public StudentUpdateRequest(String name, String lastName) {
        super(name, lastName);
    }
}
