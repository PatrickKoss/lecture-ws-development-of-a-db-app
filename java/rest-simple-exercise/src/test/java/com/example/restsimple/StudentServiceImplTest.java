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
        throw new UnsupportedOperationException("Not implemented");
    }

    @Test
    public void testCreateStudent() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Test
    public void testUpdateStudent() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Test
    public void testUpdateStudent_NotFound() {
        throw new UnsupportedOperationException("Not implemented");
    }


    @Test
    public void testDeleteStudent() {
        throw new UnsupportedOperationException("Not implemented");
    }


    @Test
    public void testDeleteStudent_NotFound() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Test
    public void testFindStudentById() {
        throw new UnsupportedOperationException("Not implemented");
    }
}
