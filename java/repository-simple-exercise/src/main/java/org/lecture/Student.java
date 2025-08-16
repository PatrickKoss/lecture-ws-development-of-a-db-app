package org.lecture;

import java.time.LocalDate;

public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String studentNumber;
    private LocalDate enrollmentDate;

    public Student() {}

    public Student(String firstName, String lastName, String email, String studentNumber, LocalDate enrollmentDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.studentNumber = studentNumber;
        this.enrollmentDate = enrollmentDate;
    }

    public Student(int id, String firstName, String lastName, String email, String studentNumber, LocalDate enrollmentDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.studentNumber = studentNumber;
        this.enrollmentDate = enrollmentDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getStudentNumber() { return studentNumber; }
    public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber; }
    
    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(LocalDate enrollmentDate) { this.enrollmentDate = enrollmentDate; }

    @Override
    public String toString() {
        return String.format("ID: %d | Name: %s %s | Email: %s | Student#: %s | Enrolled: %s",
                id, firstName, lastName, email, studentNumber, enrollmentDate);
    }
}