package com.example.restsimple.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.restsimple.domain.Student;
import com.example.restsimple.exception.ResourceConflictException;
import com.example.restsimple.exception.ResourceNotFoundException;
import com.example.restsimple.repository.StudentRepository;
import java.time.LocalDate;
import java.time.Clock;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock StudentRepository repository;
    StudentService service;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        service = new StudentService(repository,
                Clock.fixed(java.time.Instant.parse("2026-04-01T00:00:00Z"), ZoneOffset.UTC));
    }

    @Test
    void returnsAllStudents() {
        Student ada = student(1L, "Ada", "Lovelace", "ada@hs.example", "M2026001");
        when(repository.findAll()).thenReturn(List.of(ada));

        assertThat(service.findAll()).containsExactly(ada);
    }

    @Test
    void reportsMissingStudent() {
        when(repository.findById(42L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(42L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void rejectsDuplicateStudentNumber() {
        var command = new CreateStudentCommand("Ada", "Lovelace", "ada@hs.example", "M2026001");
        when(repository.existsByStudentNumber("M2026001")).thenReturn(true);

        assertThatThrownBy(() -> service.create(command))
                .isInstanceOf(ResourceConflictException.class);
    }

    @Test
    void createsStudentWithTodaysDate() {
        var command = new CreateStudentCommand("Ada", "Lovelace", "ada@hs.example", "M2026001");
        Student saved = student(1L, "Ada", "Lovelace", "ada@hs.example", "M2026001");
        when(repository.save(any(Student.class))).thenReturn(saved);

        assertThat(service.create(command)).isEqualTo(saved);
        verify(repository).save(argThat(value ->
                value.id() == null
                        && value.firstName().equals("Ada")
                        && value.lastName().equals("Lovelace")
                        && value.email().equals("ada@hs.example")
                        && value.studentNumber().equals("M2026001")
                        && value.enrollmentDate().equals(LocalDate.of(2026, 4, 1))));
    }

    private static Student student(
            Long id, String firstName, String lastName, String email, String studentNumber) {
        return new Student(id, firstName, lastName, email, studentNumber, LocalDate.now());
    }
}
