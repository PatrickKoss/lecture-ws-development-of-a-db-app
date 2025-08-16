package org.lecture;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Student Management System CLI
 * 
 * This application provides a command-line interface for managing student records.
 * It demonstrates the use of the Repository pattern for database operations.
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static StudentRepository studentRepository;

    public static void main(String[] args) {
        System.out.println("=== Student Management System ===");
        System.out.println("Initializing database...");
        
        // TODO: Uncomment this line once StudentRepositoryImpl is implemented
        // studentRepository = new StudentRepositoryImpl();
        // studentRepository.initializeDatabase();
        
        System.out.println("Database initialized successfully.");
        
        while (true) {
            showMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    createStudent();
                    break;
                case 2:
                    listAllStudents();
                    break;
                case 3:
                    findStudentById();
                    break;
                case 4:
                    updateStudent();
                    break;
                case 5:
                    deleteStudent();
                    break;
                case 6:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        }
    }

    private static void showMenu() {
        System.out.println("\n--- Student Management Menu ---");
        System.out.println("1. Create Student");
        System.out.println("2. List All Students");
        System.out.println("3. Find Student by ID");
        System.out.println("4. Update Student");
        System.out.println("5. Delete Student");
        System.out.println("6. Exit");
    }

    private static void createStudent() {
        System.out.println("\n--- Create New Student ---");
        
        String firstName = getStringInput("Enter first name: ");
        String lastName = getStringInput("Enter last name: ");
        String email = getStringInput("Enter email: ");
        String studentNumber = getStringInput("Enter student number: ");
        LocalDate enrollmentDate = getDateInput("Enter enrollment date (YYYY-MM-DD): ");

        Student student = new Student(firstName, lastName, email, studentNumber, enrollmentDate);
        
        // TODO: Uncomment this block once StudentRepositoryImpl is implemented
        /*
        try {
            Student createdStudent = studentRepository.createStudent(student);
            if (createdStudent != null) {
                System.out.println("Student created successfully!");
                System.out.println(createdStudent);
            } else {
                System.out.println("Failed to create student.");
            }
        } catch (Exception e) {
            System.err.println("Error creating student: " + e.getMessage());
        }
        */
        
        System.out.println("TODO: Implement StudentRepository to enable this functionality");
    }

    private static void listAllStudents() {
        System.out.println("\n--- All Students ---");
        
        // TODO: Uncomment this block once StudentRepositoryImpl is implemented
        /*
        try {
            List<Student> students = studentRepository.findAllStudents();
            if (students.isEmpty()) {
                System.out.println("No students found.");
            } else {
                for (Student student : students) {
                    System.out.println(student);
                }
            }
        } catch (Exception e) {
            System.err.println("Error listing students: " + e.getMessage());
        }
        */
        
        System.out.println("TODO: Implement StudentRepository to enable this functionality");
    }

    private static void findStudentById() {
        System.out.println("\n--- Find Student by ID ---");
        
        int id = getIntInput("Enter student ID: ");
        
        // TODO: Uncomment this block once StudentRepositoryImpl is implemented
        /*
        try {
            Optional<Student> student = studentRepository.findStudentById(id);
            if (student.isPresent()) {
                System.out.println("Student found:");
                System.out.println(student.get());
            } else {
                System.out.println("No student found with ID: " + id);
            }
        } catch (Exception e) {
            System.err.println("Error finding student: " + e.getMessage());
        }
        */
        
        System.out.println("TODO: Implement StudentRepository to enable this functionality");
    }

    private static void updateStudent() {
        System.out.println("\n--- Update Student ---");
        
        int id = getIntInput("Enter student ID to update: ");
        
        // TODO: Uncomment this block once StudentRepositoryImpl is implemented
        /*
        try {
            if (!studentRepository.studentExists(id)) {
                System.out.println("No student found with ID: " + id);
                return;
            }
            
            String firstName = getStringInput("Enter new first name: ");
            String lastName = getStringInput("Enter new last name: ");
            String email = getStringInput("Enter new email: ");
            String studentNumber = getStringInput("Enter new student number: ");
            LocalDate enrollmentDate = getDateInput("Enter new enrollment date (YYYY-MM-DD): ");

            Student student = new Student(id, firstName, lastName, email, studentNumber, enrollmentDate);
            
            if (studentRepository.updateStudent(student)) {
                System.out.println("Student updated successfully!");
            } else {
                System.out.println("Failed to update student.");
            }
        } catch (Exception e) {
            System.err.println("Error updating student: " + e.getMessage());
        }
        */
        
        System.out.println("TODO: Implement StudentRepository to enable this functionality");
    }

    private static void deleteStudent() {
        System.out.println("\n--- Delete Student ---");
        
        int id = getIntInput("Enter student ID to delete: ");
        
        // TODO: Uncomment this block once StudentRepositoryImpl is implemented
        /*
        try {
            if (!studentRepository.studentExists(id)) {
                System.out.println("No student found with ID: " + id);
                return;
            }
            
            String confirm = getStringInput("Are you sure you want to delete this student? (yes/no): ");
            if (!confirm.equalsIgnoreCase("yes")) {
                System.out.println("Delete cancelled.");
                return;
            }
            
            if (studentRepository.deleteStudent(id)) {
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("Failed to delete student.");
            }
        } catch (Exception e) {
            System.err.println("Error deleting student: " + e.getMessage());
        }
        */
        
        System.out.println("TODO: Implement StudentRepository to enable this functionality");
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static LocalDate getDateInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return LocalDate.parse(scanner.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Please enter date in YYYY-MM-DD format.");
            }
        }
    }
}