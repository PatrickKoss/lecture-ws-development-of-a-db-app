package com.example.restsimple.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.repository.query.Param;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Table(name = "student")
public class Student {
    public Student(String id, String name, String lastName, LocalDateTime createdOn) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.createdOn = createdOn;
    }

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
    private LocalDateTime createdOn;

    public Student() {

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

    public void setId(String id) {
        this.id = id;
    }

    public void setMnr(String mnr) {
        this.mnr = mnr;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }
}
