package com.example.restsimple.adapter.out.dto;

import com.example.restsimple.converter.LocalDateTimeAttributeConverter;
import com.example.restsimple.domain.model.Student;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "student")
public class StudentJpaEntity {
    @Column(name = "id", nullable = false)
    @NotNull(message = "id is required")
    private String id;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mnr", nullable = false)
    private String mnr;
    
    @Column(name = "name", nullable = false)
    @Size(max = 200, message = "Name must not exceed 200 characters")
    @NotNull(message = "name is required")
    private String name;
    
    @Column(name = "last_name", nullable = false)
    @NotNull(message = "lastName is required")
    private String lastName;
    
    @Column(name = "created_on", nullable = false)
    @NotNull(message = "created is required")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime createdOn;

    public StudentJpaEntity() {}

    public StudentJpaEntity(String id, String name, String lastName, LocalDateTime createdOn) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.createdOn = createdOn;
    }

    public static StudentJpaEntity fromDomain(Student student) {
        StudentJpaEntity entity = new StudentJpaEntity();
        entity.id = student.getId();
        entity.mnr = student.getMnr();
        entity.name = student.getName();
        entity.lastName = student.getLastName();
        entity.createdOn = student.getCreatedOn();
        return entity;
    }

    public Student toDomain() {
        return new Student(id, mnr, name, lastName, createdOn);
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMnr() {
        return mnr;
    }

    public void setMnr(String mnr) {
        this.mnr = mnr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }
}