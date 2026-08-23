package org.lecture;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class StudentRepository implements AbstractRepository<Student>, AutoCloseable {
    private final Connection connection;

    public StudentRepository() throws Exception {
        connection = DriverManager.getConnection("jdbc:sqlite:students.db");

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

        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public Class<Student> getClassType() {
        return Student.class;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
