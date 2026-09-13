package org.lecture;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class JdbcStudentRepository implements StudentRepository {
    private static final String COLUMNS =
            "id, first_name, last_name, email, student_number, enrollment_date";
    private final Database database;

    public JdbcStudentRepository(Database database) {
        this.database = database;
    }

    @Override
    public List<Student> findAll() throws SQLException {
        String sql = "SELECT " + COLUMNS + " FROM students ORDER BY id";
        var students = new ArrayList<Student>();
        try (var connection = database.open();
                var statement = connection.prepareStatement(sql);
                var rows = statement.executeQuery()) {
            while (rows.next()) {
                students.add(map(rows));
            }
        }
        return students;
    }

    @Override
    public Optional<Student> findById(long id) throws SQLException {
        String sql = "SELECT " + COLUMNS + " FROM students WHERE id = ?";
        try (var connection = database.open();
                var statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (var rows = statement.executeQuery()) {
                return rows.next() ? Optional.of(map(rows)) : Optional.empty();
            }
        }
    }

    private Student map(ResultSet row) throws SQLException {
        return new Student(
                row.getLong("id"),
                row.getString("first_name"),
                row.getString("last_name"),
                row.getString("email"),
                row.getString("student_number"),
                LocalDate.parse(row.getString("enrollment_date")));
    }
}
