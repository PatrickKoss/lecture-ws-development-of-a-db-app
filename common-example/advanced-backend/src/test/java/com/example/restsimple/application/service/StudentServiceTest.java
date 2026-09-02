package com.example.restsimple.application.service;

import com.example.restsimple.application.port.in.CreateStudentUseCase;
import com.example.restsimple.application.port.in.UpdateStudentUseCase;
import com.example.restsimple.application.port.out.DeleteStudentPort;
import com.example.restsimple.application.port.out.LoadStudentPort;
import com.example.restsimple.application.port.out.SaveStudentPort;
import com.example.restsimple.domain.exception.StudentNotFoundException;
import com.example.restsimple.domain.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private LoadStudentPort loadStudentPort;

    @Mock
    private SaveStudentPort saveStudentPort;

    @Mock
    private DeleteStudentPort deleteStudentPort;

    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentService(loadStudentPort, saveStudentPort, deleteStudentPort);
    }

    @Test
    void createStudent_ShouldReturnCreatedStudent() {
        // Given
        CreateStudentUseCase.CreateStudentCommand command = 
            new CreateStudentUseCase.CreateStudentCommand("John", "Doe");
        Student expectedStudent = new Student("1", null, "John", "Doe", LocalDateTime.now());
        
        when(saveStudentPort.saveStudent(any(Student.class))).thenReturn(expectedStudent);

        // When
        Student result = studentService.createStudent(command);

        // Then
        assertNotNull(result);
        assertEquals("John", result.getName());
        assertEquals("Doe", result.getLastName());
        verify(saveStudentPort).saveStudent(any(Student.class));
    }

    @Test
    void getAllStudents_ShouldReturnAllStudents() {
        // Given
        List<Student> students = List.of(
            new Student("1", null, "John", "Doe", LocalDateTime.now()),
            new Student("2", null, "Jane", "Smith", LocalDateTime.now())
        );
        when(loadStudentPort.loadAllStudents()).thenReturn(students);

        // When
        List<Student> result = studentService.getAllStudents();

        // Then
        assertEquals(2, result.size());
        verify(loadStudentPort).loadAllStudents();
    }

    @Test
    void getStudentById_WhenStudentExists_ShouldReturnStudent() {
        // Given
        String id = "1";
        Student student = new Student(id, null, "John", "Doe", LocalDateTime.now());
        when(loadStudentPort.loadStudent(id)).thenReturn(Optional.of(student));

        // When
        Student result = studentService.getStudentById(id);

        // Then
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(loadStudentPort).loadStudent(id);
    }

    @Test
    void getStudentById_WhenStudentNotExists_ShouldThrowException() {
        // Given
        String id = "1";
        when(loadStudentPort.loadStudent(id)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(StudentNotFoundException.class, () -> studentService.getStudentById(id));
        verify(loadStudentPort).loadStudent(id);
    }

    @Test
    void updateStudent_WhenStudentExists_ShouldReturnUpdatedStudent() {
        // Given
        String id = "1";
        UpdateStudentUseCase.UpdateStudentCommand command = 
            new UpdateStudentUseCase.UpdateStudentCommand(id, "John Updated", "Doe Updated");
        Student updatedStudent = new Student(id, "20250001", "John Updated", "Doe Updated", LocalDateTime.now());
        
        when(saveStudentPort.updateStudentNames(id, "John Updated", "Doe Updated")).thenReturn(Optional.of(updatedStudent));

        // When
        Student result = studentService.updateStudent(command);

        // Then
        assertNotNull(result);
        assertEquals("John Updated", result.getName());
        assertEquals("Doe Updated", result.getLastName());
        verify(saveStudentPort).updateStudentNames(id, "John Updated", "Doe Updated");
        verifyNoInteractions(loadStudentPort);
    }

    @Test
    void updateStudent_WhenStudentNotExists_ShouldThrowException() {
        // Given
        String id = "1";
        UpdateStudentUseCase.UpdateStudentCommand command = 
            new UpdateStudentUseCase.UpdateStudentCommand(id, "John", "Doe");
        when(saveStudentPort.updateStudentNames(id, "John", "Doe")).thenReturn(Optional.empty());

        // When & Then
        assertThrows(StudentNotFoundException.class, () -> studentService.updateStudent(command));
        verify(saveStudentPort).updateStudentNames(id, "John", "Doe");
        verifyNoInteractions(loadStudentPort);
    }

    @Test
    void updateStudent_WhenDuplicateNameExists_ShouldAllowUpdate() {
        // Given
        String id = "1";
        UpdateStudentUseCase.UpdateStudentCommand command = 
            new UpdateStudentUseCase.UpdateStudentCommand(id, "Jane", "Smith");
        Student updatedStudent = new Student(id, "20250001", "Jane", "Smith", LocalDateTime.now());
        
        when(saveStudentPort.updateStudentNames(id, "Jane", "Smith")).thenReturn(Optional.of(updatedStudent));

        // When
        Student result = studentService.updateStudent(command);

        // Then
        assertNotNull(result);
        assertEquals("Jane", result.getName());
        assertEquals("Smith", result.getLastName());
        verify(saveStudentPort).updateStudentNames(id, "Jane", "Smith");
        verifyNoInteractions(loadStudentPort);
    }

    @Test
    void deleteStudent_WhenStudentExists_ShouldReturnDeletedStudent() {
        // Given
        String id = "1";
        Student student = new Student(id, null, "John", "Doe", LocalDateTime.now());
        when(deleteStudentPort.deleteStudentAndReturn(id)).thenReturn(Optional.of(student));

        // When
        Student result = studentService.deleteStudent(id);

        // Then
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(deleteStudentPort).deleteStudentAndReturn(id);
    }

    @Test
    void deleteStudent_WhenStudentNotExists_ShouldThrowException() {
        // Given
        String id = "1";
        when(deleteStudentPort.deleteStudentAndReturn(id)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(StudentNotFoundException.class, () -> studentService.deleteStudent(id));
        verify(deleteStudentPort).deleteStudentAndReturn(id);
    }
}