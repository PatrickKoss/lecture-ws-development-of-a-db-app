package com.example.restsimple.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Admin {
    private final Long id;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String passwordHash;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public Admin(Long id, String username, String firstName, String lastName, String email, String passwordHash, LocalDateTime createdAt, LocalDateTime updatedAt) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (username.length() < 3 || username.length() > 50) {
            throw new IllegalArgumentException("Username must be between 3 and 50 characters");
        }
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        if (firstName.length() > 100) {
            throw new IllegalArgumentException("First name must not exceed 100 characters");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
        if (lastName.length() > 100) {
            throw new IllegalArgumentException("Last name must not exceed 100 characters");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (passwordHash == null || passwordHash.trim().isEmpty()) {
            throw new IllegalArgumentException("Password hash cannot be null or empty");
        }

        this.id = id;
        this.username = username.trim();
        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
        this.email = email.trim().toLowerCase();
        this.passwordHash = passwordHash;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Admin createNew(String username, String firstName, String lastName, String email, String passwordHash) {
        LocalDateTime now = LocalDateTime.now();
        return new Admin(null, username, firstName, lastName, email, passwordHash, now, now);
    }

    public Admin withId(Long id) {
        return new Admin(id, this.username, this.firstName, this.lastName, this.email, this.passwordHash, this.createdAt, LocalDateTime.now());
    }

    public Admin withUpdatedInfo(String firstName, String lastName, String email) {
        return new Admin(this.id, this.username, firstName, lastName, email, this.passwordHash, this.createdAt, LocalDateTime.now());
    }

    public Admin withUpdatedPassword(String newPasswordHash) {
        return new Admin(this.id, this.username, this.firstName, this.lastName, this.email, newPasswordHash, this.createdAt, LocalDateTime.now());
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return Objects.equals(id, admin.id) &&
                Objects.equals(username, admin.username) &&
                Objects.equals(firstName, admin.firstName) &&
                Objects.equals(lastName, admin.lastName) &&
                Objects.equals(email, admin.email) &&
                Objects.equals(passwordHash, admin.passwordHash) &&
                Objects.equals(createdAt, admin.createdAt) &&
                Objects.equals(updatedAt, admin.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, firstName, lastName, email, passwordHash, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}