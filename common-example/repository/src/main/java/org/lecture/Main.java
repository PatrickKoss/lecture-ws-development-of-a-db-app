package org.lecture;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        try (StudentRepository repository = new StudentRepository()) {
            Student anna = new Student(
                    "Anna",
                    "Muster",
                    "anna@uni.de",
                    "M2026001",
                    "2024-10-01"
            );
            repository.create(anna);

            List<Student> students = repository.all();
            System.out.println("After create: " + students);

            Student storedAnna = repository.get(students.get(0).getId());
            System.out.println("Get by id: " + storedAnna);

            storedAnna.setFirstName("Anne");
            repository.update(storedAnna);
            System.out.println("After update: " + repository.get(storedAnna.getId()));

            repository.delete(storedAnna.getId());
            System.out.println("After delete: " + repository.all());
        }
    }
}
