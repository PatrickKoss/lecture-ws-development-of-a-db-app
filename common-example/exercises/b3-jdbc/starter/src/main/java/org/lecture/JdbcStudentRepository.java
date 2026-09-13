package org.lecture;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public final class JdbcStudentRepository implements StudentRepository {
    private final Database database;

    public JdbcStudentRepository(Database database) { this.database = database; }

    @Override
    public List<Student> findAll() throws SQLException {
        // TODO B3: SELECT explicit columns, ORDER BY id, and call map for every row.
        throw new UnsupportedOperationException("TODO B3 findAll");
    }

    @Override
    public Optional<Student> findById(long id) throws SQLException {
        // TODO B3: Use WHERE id = ? and bind id on a PreparedStatement.
        throw new UnsupportedOperationException("TODO B3 findById");
    }

    private Student map(ResultSet row) throws SQLException {
        // TODO B3: Map snake_case columns and parse enrollment_date with LocalDate.parse.
        throw new UnsupportedOperationException("TODO B3 map");
    }
}
