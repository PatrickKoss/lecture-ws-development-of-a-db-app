package com.example.restsimple;

import com.example.restsimple.model.Student;
import com.example.restsimple.repository.StudentRepository;
import com.example.restsimple.repository.UnitOfWork;
import com.example.restsimple.repository.UnitOfWork.TransactionCode;
import com.example.restsimple.repository.UpdateStudent;
import com.example.restsimple.service.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private UnitOfWork unitOfWork;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    public void testGetAllStudents() {
        List<Student> students = Arrays.asList(new Student());
        when(unitOfWork.executeInTransaction(any())).thenReturn(students);

        List<Student> result = studentService.getAllStudents();

        assertEquals(students, result);
    }

    @Test
    public void testCreateStudent() {
        Student student = new Student();
        when(unitOfWork.executeInTransaction(any())).thenReturn(student);

        Student result = studentService.createStudent(student);

        assertEquals(student, result);
    }

    @Test
    public void testUpdateStudent() {
        String id = "1";
        UpdateStudent updateStudent = new UpdateStudent("John", "Doe");
        Student existingStudent = new Student();
        when(unitOfWork.executeInTransaction(any(TransactionCode.class))).thenAnswer(invocation -> {
            TransactionCode<Student> code = invocation.getArgument(0);
            return code.apply();
        });
        when(studentRepository.findByIdColumn(id)).thenReturn(Optional.of(existingStudent));

        Optional<Student> result = studentService.updateStudent(id, updateStudent);

        assertEquals(existingStudent, result.get());
        assertEquals("John", result.get().getName());
        assertEquals("Doe", result.get().getLastName());
    }

    @Test
    public void testUpdateStudent_NotFound() {
        String id = "1";
        UpdateStudent updateStudent = new UpdateStudent("John", "Doe");

        when(unitOfWork.executeInTransaction(any(TransactionCode.class))).thenAnswer(invocation -> {
            TransactionCode<Optional<Student>> code = invocation.getArgument(0);
            return code.apply();
        });

        when(studentRepository.findByIdColumn(id)).thenReturn(Optional.empty());

        Optional<Student> result = studentService.updateStudent(id, updateStudent);

        assertFalse(result.isPresent());
    }


    @Test
    public void testDeleteStudent() {
        String id = "1";
        Student student = new Student();
        when(unitOfWork.executeInTransaction(any(TransactionCode.class))).thenAnswer(invocation -> {
            TransactionCode<Student> code = invocation.getArgument(0);
            return code.apply();
        });
        when(studentRepository.findByIdColumn(id)).thenReturn(Optional.of(student));

        Optional<Student> result = studentService.deleteStudent(id);

        assertEquals(student, result.get());
        verify(studentRepository).delete(student);
    }


    @Test
    public void testDeleteStudent_NotFound() {
        String id = "1";
        when(unitOfWork.executeInTransaction(any(TransactionCode.class))).thenAnswer(invocation -> {
            TransactionCode<Optional<Student>> code = invocation.getArgument(0);
            return code.apply();
        });
        when(studentRepository.findByIdColumn(id)).thenReturn(Optional.empty());

        Optional<Student> result = studentService.deleteStudent(id);

        assertFalse(result.isPresent());
    }

    @Test
    public void testFindStudentById() {
        String id = "1";
        Student student = new Student();
        when(unitOfWork.executeInTransaction(any(TransactionCode.class))).thenAnswer(invocation -> {
            TransactionCode<Optional<Student>> code = invocation.getArgument(0);
            return code.apply();
        });
        when(studentRepository.findByIdColumn(id)).thenReturn(Optional.of(student));

        Optional<Student> result = studentService.findStudentById(id);

        assertEquals(student, result.get());
    }
}
