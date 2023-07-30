package com.example.restsimple.model;

import javax.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "student")
public class Student {
    public Student( String id, String mnr, String name, String lastName, LocalDateTime createdOn) {
        this.id = id;
        this.mnr = mnr;
        this.name = name;
        this.lastName = lastName;
        this.createdOn = createdOn;
    }

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "mnr", nullable = false)
    private String mnr;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "created_on", nullable = false)
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
