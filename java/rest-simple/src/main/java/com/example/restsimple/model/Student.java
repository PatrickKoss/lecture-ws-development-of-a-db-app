package com.example.restsimple.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "student")
public class Student {
    public Student(String id, String mnr, String name, String lastName, LocalDateTime createdOn) {
        this.id = id;
        this.mnr = mnr;
        this.name = name;
        this.lastName = lastName;
        this.createdOn = createdOn;
    }

    @Id
    @ApiModelProperty(hidden = true)
    @Column(name = "id", nullable = false)
    private String id;

    @JsonProperty("martriclenumber")
    @JsonAlias({"martriclenumber", "mnr"})
    @Column(name = "mnr", nullable = false)
    @NotNull(message = "mnr is required")
    private String mnr;

    @Column(name = "name", nullable = false)
    @Size(max = 10, message = "Name must not exceed 200 characters")
    @NotNull(message = "Name is required")
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
