package com.example.restsimple.repository;

import com.example.restsimple.domain.Student;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
class StudentJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name", nullable = false) private String firstName;
    @Column(name = "last_name", nullable = false) private String lastName;
    @Column(nullable = false, unique = true) private String email;
    @Column(name = "student_number", nullable = false, unique = true) private String studentNumber;
    @Column(name = "enrollment_date", nullable = false) private String enrollmentDate;

    protected StudentJpaEntity() {}

    private StudentJpaEntity(Long id, String firstName, String lastName, String email,
            String studentNumber, String enrollmentDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.studentNumber = studentNumber;
        this.enrollmentDate = enrollmentDate;
    }

    static StudentJpaEntity fromDomain(Student value) {
        return new StudentJpaEntity(value.id(), value.firstName(), value.lastName(), value.email(),
                value.studentNumber(), value.enrollmentDate().toString());
    }

    Student toDomain() {
        return new Student(id, firstName, lastName, email, studentNumber,
                java.time.LocalDate.parse(enrollmentDate));
    }
}
