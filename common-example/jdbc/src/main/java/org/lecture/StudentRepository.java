package org.lecture;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    List<Student> findAll() throws SQLException;

    Optional<Student> findById(long id) throws SQLException;
}
