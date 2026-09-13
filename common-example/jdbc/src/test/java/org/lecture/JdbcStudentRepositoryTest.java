package org.lecture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class JdbcStudentRepositoryTest {
    @Test
    void readsCanonicalSeedAndMissingId() throws Exception {
        Path file = Files.createTempFile("university-jdbc-", ".db");
        try {
            Database database = database(file);
            database.initialize();
            var repository = new JdbcStudentRepository(database);

            assertEquals(20, repository.findAll().size());
            assertEquals(
                    new Student(
                            1L,
                            "Lena",
                            "Hoffmann",
                            "lena.hoffmann@stud.example",
                            "M2023001",
                            LocalDate.of(2023, 10, 1)),
                    repository.findById(1).orElseThrow());
            assertTrue(repository.findById(99999).isEmpty());
            assertEquals(java.util.List.of("INF", "MAT", "WI"), new DepartmentLookup(database).findCodes());
        } finally {
            Files.deleteIfExists(file);
        }
    }

    @Test
    void enablesForeignKeysForEveryConnection() throws Exception {
        Path file = Files.createTempFile("university-jdbc-", ".db");
        try {
            Database database = database(file);
            database.initialize();
            try (var connection = database.open();
                    var statement = connection.prepareStatement(
                            "INSERT INTO courses(course_code,title,credits,lecturer_id) VALUES (?,?,?,?)")) {
                statement.setString(1, "BAD-1");
                statement.setString(2, "Ungültiger Kurs");
                statement.setInt(3, 5);
                statement.setLong(4, 99999);
                assertThrows(SQLException.class, statement::executeUpdate);
            }
        } finally {
            Files.deleteIfExists(file);
        }
    }

    private Database database(Path file) {
        return new Database(
                "jdbc:sqlite:" + file,
                Path.of("../sql/schema.sql"),
                Path.of("../sql/seed.sql"));
    }
}
