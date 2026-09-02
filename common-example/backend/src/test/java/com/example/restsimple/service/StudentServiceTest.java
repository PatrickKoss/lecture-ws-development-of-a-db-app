package com.example.restsimple.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.restsimple.domain.Student;
import com.example.restsimple.dto.CreateStudentRequest;
import com.example.restsimple.exception.ResourceConflictException;
import com.example.restsimple.exception.ResourceNotFoundException;
import com.example.restsimple.repository.StudentRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Disabled("Nach der gemeinsamen Implementierung von StudentService aktivieren")
@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock StudentRepository repository;
    @InjectMocks StudentService service;

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
        var request = new CreateStudentRequest("Ada", "Lovelace", "ada@hs.example", "M2026001");
        when(repository.existsByStudentNumber("M2026001")).thenReturn(true);

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(ResourceConflictException.class);
    }

    @Test
    void createsStudentWithTodaysDate() {
        var request = new CreateStudentRequest("Ada", "Lovelace", "ada@hs.example", "M2026001");
        Student saved = student(1L, "Ada", "Lovelace", "ada@hs.example", "M2026001");
        when(repository.insert(any(Student.class))).thenReturn(saved);

        assertThat(service.create(request)).isEqualTo(saved);
        verify(repository).insert(any(Student.class));
    }

    private static Student student(
            Long id, String firstName, String lastName, String email, String studentNumber) {
        return new Student(id, firstName, lastName, email, studentNumber, LocalDate.now());
    }
}
