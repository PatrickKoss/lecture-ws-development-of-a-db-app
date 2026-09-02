package com.example.restsimple.repository;

import com.example.restsimple.domain.Student;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcStudentRepository implements StudentRepository {
    private static final RowMapper<Student> ROW_MAPPER = JdbcStudentRepository::mapRow;
    private final JdbcTemplate jdbc;

    public JdbcStudentRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Student> findAll() {
        return jdbc.query("""
                SELECT id, first_name, last_name, email, student_number, enrollment_date
                FROM students
                ORDER BY last_name, first_name
                """, ROW_MAPPER);
    }

    @Override
    public Optional<Student> findById(long id) {
        return jdbc.query("""
                SELECT id, first_name, last_name, email, student_number, enrollment_date
                FROM students
                WHERE id = ?
                """, ROW_MAPPER, id).stream().findFirst();
    }

    @Override
    public boolean existsByEmail(String email) {
        Integer count = jdbc.queryForObject(
                "SELECT COUNT(*) FROM students WHERE email = ?", Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public boolean existsByStudentNumber(String studentNumber) {
        Integer count = jdbc.queryForObject(
                "SELECT COUNT(*) FROM students WHERE student_number = ?",
                Integer.class,
                studentNumber);
        return count != null && count > 0;
    }

    @Override
    public Student insert(Student student) {
        var keys = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            var statement = connection.prepareStatement("""
                    INSERT INTO students (
                        first_name, last_name, email, student_number, enrollment_date
                    ) VALUES (?, ?, ?, ?, ?)
                    """, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, student.firstName());
            statement.setString(2, student.lastName());
            statement.setString(3, student.email());
            statement.setString(4, student.studentNumber());
            statement.setString(5, student.enrollmentDate().toString());
            return statement;
        }, keys);
        return findById(keys.getKey().longValue()).orElseThrow();
    }

    private static Student mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        return new Student(
                resultSet.getLong("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("email"),
                resultSet.getString("student_number"),
                resultSet.getDate("enrollment_date").toLocalDate());
    }
}
