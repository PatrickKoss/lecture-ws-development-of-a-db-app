package org.lecture;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public final class InMemoryStudentRepository implements StudentRepository {
    private final List<Student> students;

    public InMemoryStudentRepository(List<Student> students) {
        this.students = new ArrayList<>(students);
    }

    @Override
    public List<Student> findAll() {
        return students.stream().sorted(Comparator.comparing(Student::id)).toList();
    }

    @Override
    public Optional<Student> findById(long id) {
        return students.stream().filter(student -> student.id() == id).findFirst();
    }
}
