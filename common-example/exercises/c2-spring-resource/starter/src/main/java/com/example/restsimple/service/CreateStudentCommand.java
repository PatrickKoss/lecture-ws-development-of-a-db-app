package com.example.restsimple.service;

public record CreateStudentCommand(
        String firstName, String lastName, String email, String studentNumber) {}
