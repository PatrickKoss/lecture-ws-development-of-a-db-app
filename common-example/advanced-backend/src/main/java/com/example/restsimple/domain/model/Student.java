package com.example.restsimple.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Student {
    private final String id;
    private final String mnr;
    private final String name;
    private final String lastName;
    private final LocalDateTime createdOn;

    public Student(String id, String mnr, String name, String lastName, LocalDateTime createdOn) {
        this.id = Objects.requireNonNull(id, "id cannot be null");
        this.mnr = mnr;
        this.name = Objects.requireNonNull(name, "name cannot be null");
        this.lastName = Objects.requireNonNull(lastName, "lastName cannot be null");
        this.createdOn = Objects.requireNonNull(createdOn, "createdOn cannot be null");
        
        if (name.length() > 200) {
            throw new IllegalArgumentException("Name must not exceed 200 characters");
        }
    }

    public Student withUpdatedInfo(String newName, String newLastName) {
        return new Student(this.id, this.mnr, newName, newLastName, this.createdOn);
    }

    public String getId() {
        return id;
    }

    public String getMnr() {
        return mnr;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", mnr='" + mnr + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", createdOn=" + createdOn +
                '}';
    }
}