package org.lecture;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Scanner;

public class MainRefactored {
    private static final String DATABASE_URL = "jdbc:sqlite:students.db";
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        initializeDatabase();
        System.out.println("=== Student Management System ===");

        while (true) {
            showMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1 -> createStudent();
                case 2 -> listAllStudents();
                case 3 -> readStudent();
                case 4 -> updateStudent();
                case 5 -> deleteStudent();
                case 6 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n--- Menu ---");
        System.out.println("1. Create Student");
        System.out.println("2. List All Students");
        System.out.println("3. Read Student by ID");
        System.out.println("4. Update Student");
        System.out.println("5. Delete Student");
        System.out.println("6. Exit");
    }

    private static void initializeDatabase() {
        String sql = """
                CREATE TABLE IF NOT EXISTS students (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    first_name TEXT NOT NULL,
                    last_name TEXT NOT NULL,
                    email TEXT NOT NULL UNIQUE,
                    student_number TEXT NOT NULL UNIQUE,
                    enrollment_date TEXT NOT NULL
                        CHECK (
                            date(enrollment_date) IS NOT NULL
                            AND enrollment_date = date(enrollment_date)
                        )
                )
                """;

        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
            System.out.println("Database initialized successfully.");
        } catch (SQLException exception) {
            System.err.println("Error initializing database: " + exception.getMessage());
        }
    }

    private static void createStudent() {
        System.out.println("\n--- Create New Student ---");
        String firstName = getStringInput("Enter first name: ");
        String lastName = getStringInput("Enter last name: ");
        String email = getStringInput("Enter email: ");
        String studentNumber = getStringInput("Enter student number: ");
        LocalDate enrollmentDate = getDateInput("Enter enrollment date (YYYY-MM-DD): ");

        String sql = """
                INSERT INTO students (first_name, last_name, email, student_number, enrollment_date)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setString(4, studentNumber);
            statement.setString(5, enrollmentDate.toString());
            statement.executeUpdate();
            System.out.println("Student created successfully!");
        } catch (SQLException exception) {
            System.err.println("Error creating student: " + exception.getMessage());
        }
    }

    private static void listAllStudents() {
        System.out.println("\n--- All Students ---");
        String sql = """
                SELECT id, first_name, last_name, email, student_number, enrollment_date
                FROM students
                ORDER BY id
                """;

        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            boolean foundStudent = false;
            while (resultSet.next()) {
                foundStudent = true;
                System.out.println(rowToStudent(resultSet));
            }

            if (!foundStudent) {
                System.out.println("No students found.");
            }
        } catch (SQLException exception) {
            System.err.println("Error listing students: " + exception.getMessage());
        }
    }

    private static void readStudent() {
        System.out.println("\n--- Read Student ---");
        int id = getIntInput("Enter student ID: ");
        String sql = """
                SELECT id, first_name, last_name, email, student_number, enrollment_date
                FROM students
                WHERE id = ?
                """;

        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("Student found:");
                    System.out.println(rowToStudent(resultSet));
                } else {
                    System.out.println("No student found with ID: " + id);
                }
            }
        } catch (SQLException exception) {
            System.err.println("Error reading student: " + exception.getMessage());
        }
    }

    private static Student rowToStudent(ResultSet resultSet) throws SQLException {
        return new Student(
                resultSet.getInt("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("email"),
                resultSet.getString("student_number"),
                LocalDate.parse(resultSet.getString("enrollment_date"))
        );
    }

    private static void updateStudent() {
        System.out.println("\n--- Update Student ---");
        int id = getIntInput("Enter student ID to update: ");
        String firstName = getStringInput("Enter new first name: ");
        String lastName = getStringInput("Enter new last name: ");
        String email = getStringInput("Enter new email: ");
        String studentNumber = getStringInput("Enter new student number: ");
        LocalDate enrollmentDate = getDateInput("Enter new enrollment date (YYYY-MM-DD): ");

        String sql = """
                UPDATE students
                SET first_name = ?, last_name = ?, email = ?, student_number = ?, enrollment_date = ?
                WHERE id = ?
                """;

        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setString(4, studentNumber);
            statement.setString(5, enrollmentDate.toString());
            statement.setInt(6, id);

            int changedRows = statement.executeUpdate();
            if (changedRows == 0) {
                System.out.println("No student found with ID: " + id);
            } else {
                System.out.println("Student updated successfully!");
            }
        } catch (SQLException exception) {
            System.err.println("Error updating student: " + exception.getMessage());
        }
    }

    private static void deleteStudent() {
        System.out.println("\n--- Delete Student ---");
        int id = getIntInput("Enter student ID to delete: ");
        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int changedRows = statement.executeUpdate();
            if (changedRows == 0) {
                System.out.println("No student found with ID: " + id);
            } else {
                System.out.println("Student deleted successfully!");
            }
        } catch (SQLException exception) {
            System.err.println("Error deleting student: " + exception.getMessage());
        }
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return SCANNER.nextLine().trim();
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(SCANNER.nextLine().trim());
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static LocalDate getDateInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return LocalDate.parse(SCANNER.nextLine().trim());
            } catch (RuntimeException exception) {
                System.out.println("Please enter a date in YYYY-MM-DD format.");
            }
        }
    }
}
