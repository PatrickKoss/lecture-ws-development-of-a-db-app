package com.example.restsimple.response;

import com.example.restsimple.model.Student;

public class StudentUpdateResponse extends StudentCreateResponse {
    public StudentUpdateResponse(Student student) {
        super(student);
    }
}
